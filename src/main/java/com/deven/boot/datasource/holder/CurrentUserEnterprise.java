package com.deven.boot.datasource.holder;

/**
 * Created by Joshua on 2017/8/7 11:14
 */
public class CurrentUserEnterprise {

    private Long enterpriseId;

    private String enterpriseName;

    private Boolean isCurrent;

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

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }
}
