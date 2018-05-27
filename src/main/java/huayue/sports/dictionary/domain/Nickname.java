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
 * Nickname(昵称字典) 的领域层
 * Created by Mac.Manon on 2018/05/27
 * 便于用户快速取昵称。
 */

@Entity
@DynamicUpdate(true)
@Table(name="tbl_nickname")
public class Nickname implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface CheckCreate{};
    public interface CheckModify{};

    /**
     * 昵称ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long nicknameId;

    /**
     * 昵称
     */
    @NotBlank(groups={CheckCreate.class, CheckModify.class})
    @Length(min = 1, max = 50, groups={CheckCreate.class, CheckModify.class})
    //@Pattern(regexp = "", groups={CheckCreate.class, CheckModify.class})
    @Column(nullable = false, name = "name", length = 50)
    private String name = "";

    /**
     *TODO 请将数据表的名称及字段名称加入到国际化语言包中:
     TableName.nickname=\u6635\u79f0\u5b57\u5178
     FieldName.nickname.nicknameId=\u6635\u79f0ID
     FieldName.nickname.name=\u6635\u79f0
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
    public Nickname(){
        super();
    }

    /**
     *带参构造函数
     *
     */
    public Nickname(String name){
        super();
        this.name = name;
    }

    /**
     *Getter,Setter
     *
     */
    public Long getNicknameId() {
        return nicknameId;
    }

    public void setNicknameId(Long nicknameId) {
        this.nicknameId = nicknameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}