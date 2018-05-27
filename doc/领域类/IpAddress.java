package mobi.weiapp.entity;

/**
 * Created by Mac.Manon on 2018/04/17
 * 参考：https://www.qqzeng.com
 */
public class IpAddress {//IP地址
    private Long ipAddressId;//IP地址ID
    private String startIp;//开始IP
    private String endIp;//结束IP
    private Long startNum;//开始数字
    private Long endNum;//结束数字
    private Land land;//洲
    private Country country;//国家
    private Province province;//省份
    private City city;//城市
    private Area area;//区县
    private String isp;//运营商
    private String code;//行政区域
    private String longitude;//经度
    private String latitude;//纬度
    private int hascreationtime;
}
