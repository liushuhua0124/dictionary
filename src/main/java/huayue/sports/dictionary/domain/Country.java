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
 * Country(国家) 的领域层
 * Created by Mac.Manon on 2018/05/27
 */

@Entity
@DynamicUpdate(true)
@Table(name="tbl_country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface CheckCreate{};
    public interface CheckModify{};

    /**
     * 国家ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long countryId;

    /**
     * 洲
     */
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "land_id")
    @JsonIgnoreProperties("country")
    private Land land;

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
     * 国家英文
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "english", length = 50)
    private String english = "";

    /**
     * 排序
     */
    //@Range(min=value,max=value, groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "sequence")
    private Integer sequence;

    /**
     * 省份
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "country")
    @OrderBy(value = "id ASC")
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("country")
    private Set<Province> provinces;

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
     TableName.country=\u56fd\u5bb6
     FieldName.country.countryId=\u56fd\u5bb6ID
     FieldName.country.land=\u6d32
     FieldName.country.code=\u4ee3\u7801
     FieldName.country.name=\u540d\u79f0
     FieldName.country.english=\u56fd\u5bb6\u82f1\u6587
     FieldName.country.sequence=\u6392\u5e8f
     FieldName.country.provinces=\u7701\u4efd
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
    public Country(){
        super();
    }

    /**
     *带参构造函数
     *
     */
    public Country(Land land,String code,String name,String english,Integer sequence,Set<Province> provinces,long creatorUserId){
        super();
        this.land = land;
        this.code = code;
        this.name = name;
        this.english = english;
        this.sequence = sequence;
        this.provinces = provinces;
        this.creatorUserId = creatorUserId;
    }

    /**
     *带参构造函数
     *
     */
    public Country(Land land,String code,String name,String english,Integer sequence,Set<Province> provinces,Date creationTime,long creatorUserId,Date lastModificationTime,long lastModifierUserId,Boolean isDeleted,Date deletionTime,long deleterUserId){
        super();
        this.land = land;
        this.code = code;
        this.name = name;
        this.english = english;
        this.sequence = sequence;
        this.provinces = provinces;
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
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
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

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Set<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(Set<Province> provinces) {
        this.provinces = provinces;
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