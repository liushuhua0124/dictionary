package huayue.sports.dictionary.dto;

import java.io.Serializable;

/**
 * IpAddress(IP地址) 的DTO数据传输对象
 * TODO 可根据搜索时的实际需要调整字段，删除不适合用作搜索的字段
 * Created by Mac.Manon on 2018/05/27
 */

public class IpAddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字(标准查询)
     */
    private String keyword;

    /**
     * 开始IP
     */
    private String startIp;

    /**
     * 结束IP
     */
    private String endIp;

    /**
     * 运营商
     */
    private String isp;

    /**
     * 行政区域
     */
    private String code;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     *空构造函数
     *
     */
    public IpAddressDTO(){
    }

    /**
     *带参构造函数
     *
     */
    public IpAddressDTO(String keyword, String startIp, String endIp, String isp, String code, String longitude, String latitude){
        this.keyword = keyword;
        this.startIp = startIp;
        this.endIp = endIp;
        this.isp = isp;
        this.code = code;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     *Getter,Setter
     *
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}