package huayue.sports.dictionary.dto;

import java.io.Serializable;

/**
 * AdSpots(广告位) 的DTO数据传输对象
 * TODO 可根据搜索时的实际需要调整字段，删除不适合用作搜索的字段
 * Created by Mac.Manon on 2018/05/28
 */

public class AdSpotsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字(标准查询)
     */
    private String keyword;

    /**
     * 位置编码
     */
    private String placeCode;

    /**
     * 描述
     */
    private String memo;

    /**
     *空构造函数
     *
     */
    public AdSpotsDTO(){
    }

    /**
     *带参构造函数
     *
     */
    public AdSpotsDTO(String keyword, String placeCode, String memo){
        this.keyword = keyword;
        this.placeCode = placeCode;
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

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}