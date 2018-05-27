package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.IpAddress;
import huayue.sports.dictionary.dto.IpAddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 IpAddress(IP地址) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface IpAddressService {

    /**
     * 保存数据
     * @param ipAddress IP地址
     * @param request
     * @return 返回IpAddress实体
     */
    IpAddress save(IpAddress ipAddress, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param ipAddress IP地址
     * @return 返回IpAddress实体
     */
    IpAddress findOne(IpAddress ipAddress);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<IpAddress> getPageData(IpAddressDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录
     * @param ipAddress IP地址
     * @param request
     */
    void remove(IpAddress ipAddress, HttpServletRequest request) throws Exception;

}