package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.AdSpots;
import huayue.sports.dictionary.dto.AdSpotsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 AdSpots(广告位) 的服务接口层
 * Created by Mac.Manon on 2018/05/28
 */

public interface AdSpotsService {

    /**
     * 保存数据
     * @param adSpots 广告位
     * @param request
     * @return 返回AdSpots实体
     */
    AdSpots save(AdSpots adSpots, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param adSpots 广告位
     * @return 返回AdSpots实体
     */
    AdSpots findOne(AdSpots adSpots);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<AdSpots> getPageData(AdSpotsDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param adSpots 广告位
     * @param request
     */
    void remove(AdSpots adSpots, HttpServletRequest request) throws Exception;

}