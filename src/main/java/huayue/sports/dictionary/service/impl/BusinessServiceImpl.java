package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.BusinessRepository;
import huayue.sports.dictionary.domain.Business;
import huayue.sports.dictionary.dto.BusinessDTO;
import huayue.sports.dictionary.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 领域类 Business(业务) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    BusinessRepository businessRepository;

    /**
     * 保存数据
     * @param business 业务
     * @param request
     * @return 返回Business实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "business", key = "#business.businessId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为business，数据的key是business的businessId
    public Business save(Business business, HttpServletRequest request) throws Exception {

        if(business.getBusinessId()==null){
            business.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return businessRepository.save(business);
        }else{
            Business b = this.findOne(business);

            if(request.getParameterValues("businessCode") != null && !business.getBusinessCode().equals(b.getBusinessCode()))
                b.setBusinessCode(business.getBusinessCode());

            if(request.getParameterValues("memo") != null && !business.getMemo().equals(b.getMemo()))
                b.setMemo(business.getMemo());

            if(request.getParameterValues("sendSms") != null && !business.getSendSms().equals(b.getSendSms()))
                b.setSendSms(business.getSendSms());

            b.setLastModificationTime(new Date());
            b.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return businessRepository.saveAndFlush(b);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param business 业务
     * @return 返回Business实体
     */
    @Override
    @Cacheable(value = "business", key = "#business.businessId")//缓存key为business的businessId数据到缓存business中
    public Business findOne(Business business) {
        return businessRepository.findOne(business.getBusinessId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Business> getPageData(BusinessDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<Business> specification=new Specification<Business>() {
                @Override
                public Predicate toPredicate(Root<Business> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate businessCode=cb.like(cb.upper(root.get("businessCode")), "%" + keyword.toUpperCase() + "%");
                    Predicate memo=cb.like(cb.upper(root.get("memo")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(businessCode, memo));

                    return p;
                }
            };
            return businessRepository.findAll(specification,pageable);
        }

        if(dto.getBusinessCode() != null || dto.getMemo() != null){
            return businessRepository.findByBusinessCodeContainingAndMemoContainingAllIgnoringCaseAndIsDeletedFalse(dto.getBusinessCode().trim(), dto.getMemo().trim(), pageable);
        }

        return businessRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param business 业务
     * @param request
     */
    @Override
    @CacheEvict(value = "business", key = "#business.businessId")//清除key为business的businessId数据缓存
    public void remove(Business business, HttpServletRequest request) throws Exception {
        Business b = this.findOne(business);

        b.setIsDeleted(true);
        b.setDeletionTime(new Date());
        b.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        businessRepository.saveAndFlush(b);
    }

}