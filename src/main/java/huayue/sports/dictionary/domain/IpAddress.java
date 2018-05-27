package huayue.sports.dictionary.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * IpAddress(IP地址) 的领域层
 * Created by Mac.Manon on 2018/05/27
 * 参考：https://www.qqzeng.com
 */

@Entity
@DynamicUpdate(true)
@Table(name="tbl_ipaddress")
public class IpAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface CheckCreate{};
    public interface CheckModify{};

    /**
     * IP地址ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ipAddressId;

    /**
     * 开始IP
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "start_ip", length = 23)
    private String startIp = "";

    /**
     * 结束IP
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "end_ip", length = 23)
    private String endIp = "";

    /**
     * 开始数字
     */
    //@Range(min=value,max=value, groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "start_num")
    private Long startNum;

    /**
     * 结束数字
     */
    //@Range(min=value,max=value, groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "end_num")
    private Long endNum;

    /**
     * 洲
     */
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "land_id")
    @JsonIgnoreProperties("ipAddress")
    private Land land;

    /**
     * 国家
     */
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "country_id")
    @JsonIgnoreProperties("ipAddress")
    private Country country;

    /**
     * 省份
     */
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "province_id")
    @JsonIgnoreProperties("ipAddress")
    private Province province;

    /**
     * 城市
     */
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties("ipAddress")
    private City city;

    /**
     * 区县
     */
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "area_id")
    @JsonIgnoreProperties("ipAddress")
    private Area area;

    /**
     * 运营商
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "isp", length = 50)
    private String isp = "";

    /**
     * 行政区域
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "code", length = 50)
    private String code = "";

    /**
     * 经度
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "longitude", length = 50)
    private String longitude = "";

    /**
     * 纬度
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "latitude", length = 50)
    private String latitude = "";

    /**
     * 创建时间
     */
    @Column(nullable = false, name = "creation_time", updatable=false)
    private Date creationTime = new Date();

    /**
     *TODO 请将数据表的名称及字段名称加入到国际化语言包中:
     TableName.ipaddress=IP\u5730\u5740
     FieldName.ipaddress.ipAddressId=IP\u5730\u5740ID
     FieldName.ipaddress.startIp=\u5f00\u59cbIP
     FieldName.ipaddress.endIp=\u7ed3\u675fIP
     FieldName.ipaddress.startNum=\u5f00\u59cb\u6570\u5b57
     FieldName.ipaddress.endNum=\u7ed3\u675f\u6570\u5b57
     FieldName.ipaddress.land=\u6d32
     FieldName.ipaddress.country=\u56fd\u5bb6
     FieldName.ipaddress.province=\u7701\u4efd
     FieldName.ipaddress.city=\u57ce\u5e02
     FieldName.ipaddress.area=\u533a\u53bf
     FieldName.ipaddress.isp=\u8fd0\u8425\u5546
     FieldName.ipaddress.code=\u884c\u653f\u533a\u57df
     FieldName.ipaddress.longitude=\u7ecf\u5ea6
     FieldName.ipaddress.latitude=\u7eac\u5ea6
     *
     *
     *Tip:
     *如果后续加入引用类型字段，可考虑使用@Valid注解；
     *如果后续加入Collection、Map和数组类型字段，可考虑使用@Size(max, min)注解；
     */

    /**
     *空构造函数
     *
     */
    public IpAddress(){
        super();
    }

    /**
     *带参构造函数
     *
     */
    public IpAddress(String startIp,String endIp,Long startNum,Long endNum,Land land,Country country,Province province,City city,Area area,String isp,String code,String longitude,String latitude){
        super();
        this.startIp = startIp;
        this.endIp = endIp;
        this.startNum = startNum;
        this.endNum = endNum;
        this.land = land;
        this.country = country;
        this.province = province;
        this.city = city;
        this.area = area;
        this.isp = isp;
        this.code = code;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     *带参构造函数
     *
     */
    public IpAddress(String startIp,String endIp,Long startNum,Long endNum,Land land,Country country,Province province,City city,Area area,String isp,String code,String longitude,String latitude,Date creationTime){
        super();
        this.startIp = startIp;
        this.endIp = endIp;
        this.startNum = startNum;
        this.endNum = endNum;
        this.land = land;
        this.country = country;
        this.province = province;
        this.city = city;
        this.area = area;
        this.isp = isp;
        this.code = code;
        this.longitude = longitude;
        this.latitude = latitude;
        this.creationTime = creationTime;
    }

    /**
     *Getter,Setter
     *
     */
    public Long getIpAddressId() {
        return ipAddressId;
    }

    public void setIpAddressId(Long ipAddressId) {
        this.ipAddressId = ipAddressId;
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

    public Long getStartNum() {
        return startNum;
    }

    public void setStartNum(Long startNum) {
        this.startNum = startNum;
    }

    public Long getEndNum() {
        return endNum;
    }

    public void setEndNum(Long endNum) {
        this.endNum = endNum;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

}