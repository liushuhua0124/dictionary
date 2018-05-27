package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.LandRepository;
import huayue.sports.dictionary.domain.Land;
import huayue.sports.dictionary.dto.LandDTO;
import huayue.sports.dictionary.service.LandService;
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
 * 领域类 Land(洲) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class LandServiceImpl implements LandService {

    @Autowired
    LandRepository landRepository;

    /**
     * 保存数据
     * @param land 洲
     * @param request
     * @return 返回Land实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "land", key = "#land.landId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为land，数据的key是land的landId
    public Land save(Land land, HttpServletRequest request) throws Exception {

        if(land.getLandId()==null){
            land.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return landRepository.save(land);
        }else{
            Land l = this.findOne(land);

            if(request.getParameterValues("code") != null && !land.getCode().equals(l.getCode()))
                l.setCode(land.getCode());

            if(request.getParameterValues("name") != null && !land.getName().equals(l.getName()))
                l.setName(land.getName());

            if(request.getParameterValues("sequence") != null && !land.getSequence().equals(l.getSequence()))
                l.setSequence(land.getSequence());

            if(request.getParameterValues("countries") != null && !land.getCountries().equals(l.getCountries()))
                l.setCountries(land.getCountries());

            l.setLastModificationTime(new Date());
            l.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return landRepository.saveAndFlush(l);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param land 洲
     * @return 返回Land实体
     */
    @Override
    @Cacheable(value = "land", key = "#land.landId")//缓存key为land的landId数据到缓存land中
    public Land findOne(Land land) {
        return landRepository.findOne(land.getLandId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Land> getPageData(LandDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<Land> specification=new Specification<Land>() {
                @Override
                public Predicate toPredicate(Root<Land> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                    Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(code, name));

                    return p;
                }
            };
            return landRepository.findAll(specification,pageable);
        }

        if(dto.getCode() != null || dto.getName() != null){
            return landRepository.findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), pageable);
        }

        return landRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param land 洲
     * @param request
     */
    @Override
    @CacheEvict(value = "land", key = "#land.landId")//清除key为land的landId数据缓存
    public void remove(Land land, HttpServletRequest request) throws Exception {
        Land l = this.findOne(land);

        l.setIsDeleted(true);
        l.setDeletionTime(new Date());
        l.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        landRepository.saveAndFlush(l);
    }

}