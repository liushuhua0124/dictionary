package huayue.sports.dictionary.dao;

import huayue.sports.dictionary.domain.IpAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 领域类 IpAddress(IP地址) 的DAO Repository接口层
 * Created by Mac.Manon on 2018/05/27
 */

//@RepositoryRestResource(path = "newpath")
public interface IpAddressRepository extends JpaRepository<IpAddress,Long> {

    //TODO:请根据实际需要调整参数 标准查询
    @Query(value = "select i from IpAddress i where upper(i.startIp) like upper(CONCAT('%',:startIp,'%')) or upper(i.endIp) like upper(CONCAT('%',:endIp,'%')) or upper(i.isp) like upper(CONCAT('%',:isp,'%')) or upper(i.code) like upper(CONCAT('%',:code,'%')) or upper(i.longitude) like upper(CONCAT('%',:longitude,'%')) or upper(i.latitude) like upper(CONCAT('%',:latitude,'%'))")
    Page<IpAddress> standSearch(@Param("startIp")String startIp, @Param("endIp")String endIp, @Param("isp")String isp, @Param("code")String code, @Param("longitude")String longitude, @Param("latitude")String latitude, Pageable pageable);

    //TODO:请根据实际需要调整参数 高级查询
    @Query(value = "select i from IpAddress i where upper(i.startIp) like upper(CONCAT('%',:startIp,'%')) and upper(i.endIp) like upper(CONCAT('%',:endIp,'%')) and upper(i.isp) like upper(CONCAT('%',:isp,'%')) and upper(i.code) like upper(CONCAT('%',:code,'%')) and upper(i.longitude) like upper(CONCAT('%',:longitude,'%')) and upper(i.latitude) like upper(CONCAT('%',:latitude,'%'))")
    Page<IpAddress> advancedSearch(@Param("startIp")String startIp, @Param("endIp")String endIp, @Param("isp")String isp, @Param("code")String code, @Param("longitude")String longitude, @Param("latitude")String latitude, Pageable pageable);

}