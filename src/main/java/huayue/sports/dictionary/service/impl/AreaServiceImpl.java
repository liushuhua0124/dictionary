package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.AreaRepository;
import huayue.sports.dictionary.domain.Area;
import huayue.sports.dictionary.dto.AreaDTO;
import huayue.sports.dictionary.service.AreaService;
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
 * 领域类 Area(区县) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    AreaRepository areaRepository;

    /**
     * 保存数据
     * @param area 区县
     * @param request
     * @return 返回Area实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "area", key = "#area.areaId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为area，数据的key是area的areaId
    public Area save(Area area, HttpServletRequest request) throws Exception {

        if(area.getAreaId()==null){
            area.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return areaRepository.save(area);
        }else{
            Area a = this.findOne(area);

            if(request.getParameterValues("city") != null && !area.getCity().equals(a.getCity()))
                a.setCity(area.getCity());

            if(request.getParameterValues("code") != null && !area.getCode().equals(a.getCode()))
                a.setCode(area.getCode());

            if(request.getParameterValues("name") != null && !area.getName().equals(a.getName()))
                a.setName(area.getName());

            if(request.getParameterValues("sequence") != null && !area.getSequence().equals(a.getSequence()))
                a.setSequence(area.getSequence());

            a.setLastModificationTime(new Date());
            a.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return areaRepository.saveAndFlush(a);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param area 区县
     * @return 返回Area实体
     */
    @Override
    @Cacheable(value = "area", key = "#area.areaId")//缓存key为area的areaId数据到缓存area中
    public Area findOne(Area area) {
        return areaRepository.findOne(area.getAreaId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Area> getPageData(AreaDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<Area> specification=new Specification<Area>() {
                @Override
                public Predicate toPredicate(Root<Area> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                    Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(code, name));

                    return p;
                }
            };
            return areaRepository.findAll(specification,pageable);
        }

        if(dto.getCode() != null || dto.getName() != null){
            return areaRepository.findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), pageable);
        }

        return areaRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param area 区县
     * @param request
     */
    @Override
    @CacheEvict(value = "area", key = "#area.areaId")//清除key为area的areaId数据缓存
    public void remove(Area area, HttpServletRequest request) throws Exception {
        Area a = this.findOne(area);

        a.setIsDeleted(true);
        a.setDeletionTime(new Date());
        a.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        areaRepository.saveAndFlush(a);
    }

}