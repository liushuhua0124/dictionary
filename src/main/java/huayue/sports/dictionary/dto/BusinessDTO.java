package huayue.sports.dictionary.dto;

import java.io.Serializable;

/**
 * Business(业务) 的DTO数据传输对象
 * TODO 可根据搜索时的实际需要调整字段，删除不适合用作搜索的字段
 * Created by Mac.Manon on 2018/05/27
 */

public class BusinessDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字(标准查询)
     */
    private String keyword;

    /**
     * 业务代码
     */
    private String businessCode;

    /**
     * 描述
     */
    private String memo;

    /**
     *空构造函数
     *
     */
    public BusinessDTO(){
    }

    /**
     *带参构造函数
     *
     */
    public BusinessDTO(String keyword, String businessCode, String memo){
        this.keyword = keyword;
        this.businessCode = businessCode;
        this.memo = memo;
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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}