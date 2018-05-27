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
 * Province(省份) 的领域层
 * Created by Mac.Manon on 2018/05/27
 */

@Entity
@DynamicUpdate(true)
@Table(name="tbl_province")
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface CheckCreate{};
    public interface CheckModify{};

    /**
     * 省份ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long provinceId;

    /**
     * 国家
     */
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "country_id")
    @JsonIgnoreProperties("province")
    private Country country;

    /**
     * 代码
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "code", length = 50)
    private String code = "";

    /**
     * 名称
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "name", length = 50)
    private String name = "";

    /**
     * 排序
     */
    //@Range(min=value,max=value, groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "sequence")
    private Integer sequence;

    /**
     * 城市
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "province")
    @OrderBy(value = "id ASC")
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("province")
    private Set<City> cities;

    /**
     * 创建时间
     */
    @Column(nullable = false, name = "creation_time", updatable=false)
    private Date creationTime = new Date();

    /**
     * 创建者
     */
    @Column(nullable = false, name = "creator_user_id", updatable=false)
    private long creatorUserId;

    /**
     * 最近修改时间
     */
    @Column(name = "last_modification_time")
    private Date lastModificationTime;

    /**
     * 最近修改者
     */
    @Column(name = "last_modifier_user_id")
    private long lastModifierUserId;

    /**
     * 已删除
     */
    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted = false;

    /**
     * 删除时间
     */
    @Column(name = "deletion_time")
    private Date deletionTime;

    /**
     * 删除者
     */
    @Column(name = "deleter_user_id")
    private long deleterUserId;

    /**
     *TODO 请将数据表的名称及字段名称加入到国际化语言包中:
     TableName.province=\u7701\u4efd
     FieldName.province.provinceId=\u7701\u4efdID
     FieldName.province.country=\u56fd\u5bb6
     FieldName.province.code=\u4ee3\u7801
     FieldName.province.name=\u540d\u79f0
     FieldName.province.sequence=\u6392\u5e8f
     FieldName.province.cities=\u57ce\u5e02
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
    public Province(){
        super();
    }

    /**
     *带参构造函数
     *
     */
    public Province(Country country,String code,String name,Integer sequence,Set<City> cities,long creatorUserId){
        super();
        this.country = country;
        this.code = code;
        this.name = name;
        this.sequence = sequence;
        this.cities = cities;
        this.creatorUserId = creatorUserId;
    }

    /**
     *带参构造函数
     *
     */
    public Province(Country country,String code,String name,Integer sequence,Set<City> cities,Date creationTime,long creatorUserId,Date lastModificationTime,long lastModifierUserId,Boolean isDeleted,Date deletionTime,long deleterUserId){
        super();
        this.country = country;
        this.code = code;
        this.name = name;
        this.sequence = sequence;
        this.cities = cities;
        this.creationTime = creationTime;
        this.creatorUserId = creatorUserId;
        this.lastModificationTime = lastModificationTime;
        this.lastModifierUserId = lastModifierUserId;
        this.isDeleted = isDeleted;
        this.deleterUserId = deleterUserId;
        this.deletionTime = deletionTime;
    }

    /**
     *Getter,Setter
     *
     */
    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Date getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Date lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public long getLastModifierUserId() {
        return lastModifierUserId;
    }

    public void setLastModifierUserId(long lastModifierUserId) {
        this.lastModifierUserId = lastModifierUserId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(Date deletionTime) {
        this.deletionTime = deletionTime;
    }

    public long getDeleterUserId() {
        return deleterUserId;
    }

    public void setDeleterUserId(long deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

}