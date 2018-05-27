package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.Land;
import huayue.sports.dictionary.dto.LandDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Land(洲) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface LandService {

    /**
     * 保存数据
     * @param land 洲
     * @param request
     * @return 返回Land实体
     */
    Land save(Land land, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param land 洲
     * @return 返回Land实体
     */
    Land findOne(Land land);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Land> getPageData(LandDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param land 洲
     * @param request
     */
    void remove(Land land, HttpServletRequest request) throws Exception;

}