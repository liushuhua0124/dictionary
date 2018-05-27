package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.IpAddressRepository;
import huayue.sports.dictionary.domain.IpAddress;
import huayue.sports.dictionary.dto.IpAddressDTO;
import huayue.sports.dictionary.service.IpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 领域类 IpAddress(IP地址) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class IpAddressServiceImpl implements IpAddressService {

    @Autowired
    IpAddressRepository ipAddressRepository;

    /**
     * 保存数据
     * @param ipAddress IP地址
     * @param request
     * @return 返回IpAddress实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "ipAddress", key = "#ipAddress.ipAddressId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为ipAddress，数据的key是ipAddress的ipAddressId
    public IpAddress save(IpAddress ipAddress, HttpServletRequest request) throws Exception {

        if(ipAddress.getIpAddressId()==null){
            return ipAddressRepository.save(ipAddress);
        }else{
            IpAddress i = this.findOne(ipAddress);

            if(request.getParameterValues("startIp") != null && !ipAddress.getStartIp().equals(i.getStartIp()))
                i.setStartIp(ipAddress.getStartIp());

            if(request.getParameterValues("endIp") != null && !ipAddress.getEndIp().equals(i.getEndIp()))
                i.setEndIp(ipAddress.getEndIp());

            if(request.getParameterValues("startNum") != null && !ipAddress.getStartNum().equals(i.getStartNum()))
                i.setStartNum(ipAddress.getStartNum());

            if(request.getParameterValues("endNum") != null && !ipAddress.getEndNum().equals(i.getEndNum()))
                i.setEndNum(ipAddress.getEndNum());

            if(request.getParameterValues("land") != null && !ipAddress.getLand().equals(i.getLand()))
                i.setLand(ipAddress.getLand());

            if(request.getParameterValues("country") != null && !ipAddress.getCountry().equals(i.getCountry()))
                i.setCountry(ipAddress.getCountry());

            if(request.getParameterValues("province") != null && !ipAddress.getProvince().equals(i.getProvince()))
                i.setProvince(ipAddress.getProvince());

            if(request.getParameterValues("city") != null && !ipAddress.getCity().equals(i.getCity()))
                i.setCity(ipAddress.getCity());

            if(request.getParameterValues("area") != null && !ipAddress.getArea().equals(i.getArea()))
                i.setArea(ipAddress.getArea());

            if(request.getParameterValues("isp") != null && !ipAddress.getIsp().equals(i.getIsp()))
                i.setIsp(ipAddress.getIsp());

            if(request.getParameterValues("code") != null && !ipAddress.getCode().equals(i.getCode()))
                i.setCode(ipAddress.getCode());

            if(request.getParameterValues("longitude") != null && !ipAddress.getLongitude().equals(i.getLongitude()))
                i.setLongitude(ipAddress.getLongitude());

            if(request.getParameterValues("latitude") != null && !ipAddress.getLatitude().equals(i.getLatitude()))
                i.setLatitude(ipAddress.getLatitude());

            return ipAddressRepository.saveAndFlush(i);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param ipAddress IP地址
     * @return 返回IpAddress实体
     */
    @Override
    @Cacheable(value = "ipAddress", key = "#ipAddress.ipAddressId")//缓存key为ipAddress的ipAddressId数据到缓存ipAddress中
    public IpAddress findOne(IpAddress ipAddress) {
        return ipAddressRepository.findOne(ipAddress.getIpAddressId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<IpAddress> getPageData(IpAddressDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            return ipAddressRepository.standSearch(keyword, keyword, keyword, keyword, keyword, keyword, pageable);
        }

        if(dto.getStartIp() != null || dto.getEndIp() != null || dto.getIsp() != null || dto.getCode() != null || dto.getLongitude() != null || dto.getLatitude() != null){
            return ipAddressRepository.advancedSearch(dto.getStartIp().trim(), dto.getEndIp().trim(), dto.getIsp().trim(), dto.getCode().trim(), dto.getLongitude().trim(), dto.getLatitude().trim(), pageable);
        }

        return ipAddressRepository.findAll(pageable);
    }

    /**
     * 根据主键id删除一条数据记录
     * @param ipAddress IP地址
     * @param request
     */
    @Override
    @CacheEvict(value = "ipAddress", key = "#ipAddress.ipAddressId")//清除key为ipAddress的ipAddressId数据缓存
    public void remove(IpAddress ipAddress, HttpServletRequest request) throws Exception {
        ipAddressRepository.delete(ipAddress.getIpAddressId());
    }

}