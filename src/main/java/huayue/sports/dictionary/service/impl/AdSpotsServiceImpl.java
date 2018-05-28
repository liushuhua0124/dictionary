package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.AdSpotsRepository;
import huayue.sports.dictionary.domain.AdSpots;
import huayue.sports.dictionary.dto.AdSpotsDTO;
import huayue.sports.dictionary.service.AdSpotsService;
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
 * 领域类 AdSpots(广告位) 的服务实现层
 * Created by Mac.Manon on 2018/05/28
 */

@Service
public class AdSpotsServiceImpl implements AdSpotsService {

    @Autowired
    AdSpotsRepository adSpotsRepository;

    /**
     * 保存数据
     * @param adSpots 广告位
     * @param request
     * @return 返回AdSpots实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "adSpots", key = "#adSpots.adSpotsId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为adSpots，数据的key是adSpots的adSpotsId
    public AdSpots save(AdSpots adSpots, HttpServletRequest request) throws Exception {

        if(adSpots.getAdSpotsId()==null){
            adSpots.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return adSpotsRepository.save(adSpots);
        }else{
            AdSpots a = this.findOne(adSpots);

            if(request.getParameterValues("placeCode") != null && !adSpots.getPlaceCode().equals(a.getPlaceCode()))
                a.setPlaceCode(adSpots.getPlaceCode());

            if(request.getParameterValues("memo") != null && !adSpots.getMemo().equals(a.getMemo()))
                a.setMemo(adSpots.getMemo());

            a.setLastModificationTime(new Date());
            a.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return adSpotsRepository.saveAndFlush(a);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param adSpots 广告位
     * @return 返回AdSpots实体
     */
    @Override
    @Cacheable(value = "adSpots", key = "#adSpots.adSpotsId")//缓存key为adSpots的adSpotsId数据到缓存adSpots中
    public AdSpots findOne(AdSpots adSpots) {
        return adSpotsRepository.findOne(adSpots.getAdSpotsId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<AdSpots> getPageData(AdSpotsDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<AdSpots> specification=new Specification<AdSpots>() {
                @Override
                public Predicate toPredicate(Root<AdSpots> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate placeCode=cb.like(cb.upper(root.get("placeCode")), "%" + keyword.toUpperCase() + "%");
                    Predicate memo=cb.like(cb.upper(root.get("memo")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(placeCode, memo));

                    return p;
                }
            };
            return adSpotsRepository.findAll(specification,pageable);
        }

        if(dto.getPlaceCode() != null || dto.getMemo() != null){
            return adSpotsRepository.findByPlaceCodeContainingAndMemoContainingAllIgnoringCaseAndIsDeletedFalse(dto.getPlaceCode().trim(), dto.getMemo().trim(), pageable);
        }

        return adSpotsRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param adSpots 广告位
     * @param request
     */
    @Override
    @CacheEvict(value = "adSpots", key = "#adSpots.adSpotsId")//清除key为adSpots的adSpotsId数据缓存
    public void remove(AdSpots adSpots, HttpServletRequest request) throws Exception {
        AdSpots a = this.findOne(adSpots);

        a.setIsDeleted(true);
        a.setDeletionTime(new Date());
        a.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        adSpotsRepository.saveAndFlush(a);
    }

}