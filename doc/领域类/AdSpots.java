package mobi.weiapp.entity;

/**
 * Created by mac.manon on 2018/5/28.
 * 仅平台管理员可以维护广告位编码，供前端和后端开发人员协调。
 */
public class AdSpots {//广告位
    private Long adSpotsId;//广告位ID
    private String placeCode;//位置编码
    private String memo;//描述
    private int deletionaudited;
}
