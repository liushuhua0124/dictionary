package mobi.weiapp.entity;

import java.util.Set;

/**
 * Created by Mac.Manon on 2018/05/23.
 */
public class City {//城市
    private Long cityId;//城市ID
    private Province province;//省份
    private String code;//代码
    private String name;//名称
    private Integer sequence;//排序
    private Set<Area> areas;//区县
    private int deletionaudited;
}
