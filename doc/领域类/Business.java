package mobi.weiapp.entity;

/**
 * Created by mac.manon on 2018/5/23.
 * sendSms:是否发短消息。该业务发生动态时，除了在消息微服务的Remind中做站内消息提醒外，是否需要同时给用户注册手机号发短信。
 */
public class Business {//业务
    private Long businessId;//业务ID
    private String businessCode;//业务代码
    private String memo;//描述
    private Boolean sendSms;//是否发短消息
    private int deletionaudited;
}
