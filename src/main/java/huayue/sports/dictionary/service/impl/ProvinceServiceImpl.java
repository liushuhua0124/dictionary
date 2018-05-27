package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.ProvinceRepository;
import huayue.sports.dictionary.domain.Province;
import huayue.sports.dictionary.dto.ProvinceDTO;
import huayue.sports.dictionary.service.ProvinceService;
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
 * 领域类 Province(省份) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    ProvinceRepository provinceRepository;

    /**
     * 保存数据
     * @param province 省份
     * @param request
     * @return 返回Province实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "province", key = "#province.provinceId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为province，数据的key是province的provinceId
    public Province save(Province province, HttpServletRequest request) throws Exception {

        if(province.getProvinceId()==null){
            province.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return provinceRepository.save(province);
        }else{
            Province p = this.findOne(province);

            if(request.getParameterValues("country") != null && !province.getCountry().equals(p.getCountry()))
                p.setCountry(province.getCountry());

            if(request.getParameterValues("code") != null && !province.getCode().equals(p.getCode()))
                p.setCode(province.getCode());

            if(request.getParameterValues("name") != null && !province.getName().equals(p.getName()))
                p.setName(province.getName());

            if(request.getParameterValues("sequence") != null && !province.getSequence().equals(p.getSequence()))
                p.setSequence(province.getSequence());

            if(request.getParameterValues("cities") != null && !province.getCities().equals(p.getCities()))
                p.setCities(province.getCities());

            p.setLastModificationTime(new Date());
            p.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return provinceRepository.saveAndFlush(p);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param province 省份
     * @return 返回Province实体
     */
    @Override
    @Cacheable(value = "province", key = "#province.provinceId")//缓存key为province的provinceId数据到缓存province中
    public Province findOne(Province province) {
        return provinceRepository.findOne(province.getProvinceId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Province> getPageData(ProvinceDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<Province> specification=new Specification<Province>() {
                @Override
                public Predicate toPredicate(Root<Province> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                    Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(code, name));

                    return p;
                }
            };
            return provinceRepository.findAll(specification,pageable);
        }

        if(dto.getCode() != null || dto.getName() != null){
            return provinceRepository.findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), pageable);
        }

        return provinceRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param province 省份
     * @param request
     */
    @Override
    @CacheEvict(value = "province", key = "#province.provinceId")//清除key为province的provinceId数据缓存
    public void remove(Province province, HttpServletRequest request) throws Exception {
        Province p = this.findOne(province);

        p.setIsDeleted(true);
        p.setDeletionTime(new Date());
        p.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        provinceRepository.saveAndFlush(p);
    }

}