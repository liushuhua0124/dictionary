package mobi.weiapp.entity;

import java.util.Set;

/**
 * Created by Mac.Manon on 2018/05/23.
 */
public class Country {//国家
    private Long countryId;//国家ID
    private Land land;//洲
    private String code;//代码
    private String name;//名称
    private String english;//国家英文
    private Integer sequence;//排序
    private Set<Province> provinces;//省份
    private int deletionaudited;
}
