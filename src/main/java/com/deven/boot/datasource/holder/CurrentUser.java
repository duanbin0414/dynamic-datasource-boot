package com.deven.boot.datasource.holder;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * Created by Joshua on 2017/7/14 11:02
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public List<CurrentUserEnterprise> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(List<CurrentUserEnterprise> enterprises) {
        this.enterprises = enterprises;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getDingCorpId() {
        return dingCorpId;
    }

    public void setDingCorpId(String dingCorpId) {
        this.dingCorpId = dingCorpId;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Boolean getPersistent() {
        return persistent;
    }

    public void setPersistent(Boolean persistent) {
        this.persistent = persistent;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(String mobileFlag) {
        this.mobileFlag = mobileFlag;
    }
}
