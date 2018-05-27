package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.City;
import huayue.sports.dictionary.dto.CityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 City(城市) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface CityService {

    /**
     * 保存数据
     * @param city 城市
     * @param request
     * @return 返回City实体
     */
    City save(City city, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param city 城市
     * @return 返回City实体
     */
    City findOne(City city);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<City> getPageData(CityDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param city 城市
     * @param request
     */
    void remove(City city, HttpServletRequest request) throws Exception;

}