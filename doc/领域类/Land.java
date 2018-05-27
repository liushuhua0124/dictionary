package mobi.weiapp.entity;

import java.util.Set;

/**
 * Created by Mac.Manon on 2018/05/23.
 */
public class Land {//洲
    private Long landId;//洲ID
    private String code;//代码
    private String name;//名称
    private Integer sequence;//排序
    private Set<Country> countries;//国家
    private int deletionaudited;
}
