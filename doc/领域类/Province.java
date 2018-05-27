package mobi.weiapp.entity;

import java.util.Set;

/**
 * Created by Mac.Manon on 2018/05/23.
 */
public class Province {//省份
    private Long provinceId;//省份ID
    private Country country;//国家
    private String code;//代码
    private String name;//名称
    private Integer sequence;//排序
    private Set<City> cities;//城市
    private int deletionaudited;
}
