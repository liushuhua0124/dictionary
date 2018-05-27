package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.Province;
import huayue.sports.dictionary.dto.ProvinceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Province(省份) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface ProvinceService {

    /**
     * 保存数据
     * @param province 省份
     * @param request
     * @return 返回Province实体
     */
    Province save(Province province, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param province 省份
     * @return 返回Province实体
     */
    Province findOne(Province province);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Province> getPageData(ProvinceDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param province 省份
     * @param request
     */
    void remove(Province province, HttpServletRequest request) throws Exception;

}