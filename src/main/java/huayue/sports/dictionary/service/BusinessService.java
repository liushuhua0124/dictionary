package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.Business;
import huayue.sports.dictionary.dto.BusinessDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Business(业务) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface BusinessService {

    /**
     * 保存数据
     * @param business 业务
     * @param request
     * @return 返回Business实体
     */
    Business save(Business business, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param business 业务
     * @return 返回Business实体
     */
    Business findOne(Business business);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Business> getPageData(BusinessDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录(非物理删除)
     * @param business 业务
     * @param request
     */
    void remove(Business business, HttpServletRequest request) throws Exception;

}