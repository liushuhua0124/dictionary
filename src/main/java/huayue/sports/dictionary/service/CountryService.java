package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.Country;
import huayue.sports.dictionary.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Country(国家) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface CountryService {

    /**
     * 保存数据
     * @param country 国家
     * @param request
     * @return 返回Country实体
     */
    Country save(Country country, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param country 国家
     * @return 返回Country实体
     */
    Country findOne(Country country);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Country> getPageData(CountryDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param country 国家
     * @param request
     */
    void remove(Country country, HttpServletRequest request) throws Exception;

}