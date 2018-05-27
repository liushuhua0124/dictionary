package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.Area;
import huayue.sports.dictionary.dto.AreaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Area(区县) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface AreaService {

    /**
     * 保存数据
     * @param area 区县
     * @param request
     * @return 返回Area实体
     */
    Area save(Area area, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param area 区县
     * @return 返回Area实体
     */
    Area findOne(Area area);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Area> getPageData(AreaDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param area 区县
     * @param request
     */
    void remove(Area area, HttpServletRequest request) throws Exception;

}