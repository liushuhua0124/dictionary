package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.CityRepository;
import huayue.sports.dictionary.domain.City;
import huayue.sports.dictionary.dto.CityDTO;
import huayue.sports.dictionary.service.CityService;
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
 * 领域类 City(城市) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    /**
     * 保存数据
     * @param city 城市
     * @param request
     * @return 返回City实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "city", key = "#city.cityId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为city，数据的key是city的cityId
    public City save(City city, HttpServletRequest request) throws Exception {

        if(city.getCityId()==null){
            city.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return cityRepository.save(city);
        }else{
            City c = this.findOne(city);

            if(request.getParameterValues("province") != null && !city.getProvince().equals(c.getProvince()))
                c.setProvince(city.getProvince());

            if(request.getParameterValues("code") != null && !city.getCode().equals(c.getCode()))
                c.setCode(city.getCode());

            if(request.getParameterValues("name") != null && !city.getName().equals(c.getName()))
                c.setName(city.getName());

            if(request.getParameterValues("sequence") != null && !city.getSequence().equals(c.getSequence()))
                c.setSequence(city.getSequence());

            if(request.getParameterValues("areas") != null && !city.getAreas().equals(c.getAreas()))
                c.setAreas(city.getAreas());

            c.setLastModificationTime(new Date());
            c.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return cityRepository.saveAndFlush(c);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param city 城市
     * @return 返回City实体
     */
    @Override
    @Cacheable(value = "city", key = "#city.cityId")//缓存key为city的cityId数据到缓存city中
    public City findOne(City city) {
        return cityRepository.findOne(city.getCityId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<City> getPageData(CityDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<City> specification=new Specification<City>() {
                @Override
                public Predicate toPredicate(Root<City> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                    Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(code, name));

                    return p;
                }
            };
            return cityRepository.findAll(specification,pageable);
        }

        if(dto.getCode() != null || dto.getName() != null){
            return cityRepository.findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), pageable);
        }

        return cityRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param city 城市
     * @param request
     */
    @Override
    @CacheEvict(value = "city", key = "#city.cityId")//清除key为city的cityId数据缓存
    public void remove(City city, HttpServletRequest request) throws Exception {
        City c = this.findOne(city);

        c.setIsDeleted(true);
        c.setDeletionTime(new Date());
        c.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        cityRepository.saveAndFlush(c);
    }

}