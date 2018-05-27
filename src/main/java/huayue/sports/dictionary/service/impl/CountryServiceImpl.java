package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.CountryRepository;
import huayue.sports.dictionary.domain.Country;
import huayue.sports.dictionary.dto.CountryDTO;
import huayue.sports.dictionary.service.CountryService;
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
 * 领域类 Country(国家) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    /**
     * 保存数据
     * @param country 国家
     * @param request
     * @return 返回Country实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "country", key = "#country.countryId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为country，数据的key是country的countryId
    public Country save(Country country, HttpServletRequest request) throws Exception {

        if(country.getCountryId()==null){
            country.setCreatorUserId(Long.valueOf(request.getParameter("operator")));
            return countryRepository.save(country);
        }else{
            Country c = this.findOne(country);

            if(request.getParameterValues("land") != null && !country.getLand().equals(c.getLand()))
                c.setLand(country.getLand());

            if(request.getParameterValues("code") != null && !country.getCode().equals(c.getCode()))
                c.setCode(country.getCode());

            if(request.getParameterValues("name") != null && !country.getName().equals(c.getName()))
                c.setName(country.getName());

            if(request.getParameterValues("english") != null && !country.getEnglish().equals(c.getEnglish()))
                c.setEnglish(country.getEnglish());

            if(request.getParameterValues("sequence") != null && !country.getSequence().equals(c.getSequence()))
                c.setSequence(country.getSequence());

            if(request.getParameterValues("provinces") != null && !country.getProvinces().equals(c.getProvinces()))
                c.setProvinces(country.getProvinces());

            c.setLastModificationTime(new Date());
            c.setLastModifierUserId(Long.valueOf(request.getParameter("operator")));

            return countryRepository.saveAndFlush(c);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param country 国家
     * @return 返回Country实体
     */
    @Override
    @Cacheable(value = "country", key = "#country.countryId")//缓存key为country的countryId数据到缓存country中
    public Country findOne(Country country) {
        return countryRepository.findOne(country.getCountryId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Country> getPageData(CountryDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            Specification<Country> specification=new Specification<Country>() {
                @Override
                public Predicate toPredicate(Root<Country> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                    Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                    Predicate english=cb.like(cb.upper(root.get("english")), "%" + keyword.toUpperCase() + "%");
                    Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                    Predicate p = cb.and(isDeleted,cb.or(code, name, english));

                    return p;
                }
            };
            return countryRepository.findAll(specification,pageable);
        }

        if(dto.getCode() != null || dto.getName() != null || dto.getEnglish() != null){
            return countryRepository.findByCodeContainingAndNameContainingAndEnglishContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), dto.getEnglish().trim(), pageable);
        }

        return countryRepository.findByIsDeletedFalse(pageable);
    }

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param country 国家
     * @param request
     */
    @Override
    @CacheEvict(value = "country", key = "#country.countryId")//清除key为country的countryId数据缓存
    public void remove(Country country, HttpServletRequest request) throws Exception {
        Country c = this.findOne(country);

        c.setIsDeleted(true);
        c.setDeletionTime(new Date());
        c.setDeleterUserId(Long.valueOf(request.getParameter("operator")));

        countryRepository.saveAndFlush(c);
    }

}