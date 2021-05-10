package com.deven.boot.datasource.holder;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CurrentUser {

    /**
     * 主键
     */
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 账号
     * 当type为以下值的时候
     * 0.account
     * 1.account
     * 2.userId
     * 3.userId
     * 4.account
     */
    private String account;

    /**
     * 用户名
     */
    private String name;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 人员类型
     * 0.平台用户
     * 1.培训机构用户
     * 2.企业管理员
     * 3.企业员工
     * 4.闲散用户
     */
    private Integer type;

    private String typeStr;

    /**
     * 企业类型(1:普通用户 2:付费用户  3:试用用户 4:共创用户)
     */
    private Integer isVip;

    /**
     * app类型('micro_app','e_app')
     */
    private String appType;

    /**
     * 当前企业开通时间
     */
    private Date openTime;

    /**
     * 企业列表
     * 当type为2,3的时候有效
     */
    private List<CurrentUserEnterprise> enterprises;

    /**
     * 培训机构id
     */
    private Long supplierId;

    private Long enterpriseId;

    private String enterpriseName;

    private String dingCorpId;

    private String accessToken;

    /**
     * 是否持久化token
     */
    private Boolean persistent = false;

    private Boolean active;

    /**
     * 用户语言环境
     * 用户语言环境:en_us/英语_美国,zh_cn/中文_简体,zh_hk/中文_繁体_HK
     */
    private String language;

    /**
     * 用户手机号绑定标识：no_need-不需要绑定，bound-已绑定，unbound-未绑定，binding-绑定中
     */
    @JSONField(name = "mobile_flag")
    private String mobileFlag;
}
