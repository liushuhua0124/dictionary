package huayue.sports.dictionary.dto;

import java.io.Serializable;

/**
 * City(城市) 的DTO数据传输对象
 * TODO 可根据搜索时的实际需要调整字段，删除不适合用作搜索的字段
 * Created by Mac.Manon on 2018/05/27
 */

public class CityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字(标准查询)
     */
    private String keyword;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     *空构造函数
     *
     */
    public CityDTO(){
    }

    /**
     *带参构造函数
     *
     */
    public CityDTO(String keyword, String code, String name){
        this.keyword = keyword;
        this.code = code;
        this.name = name;
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

}