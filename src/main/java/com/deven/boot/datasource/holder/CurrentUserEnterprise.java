package com.deven.boot.datasource.holder;

import lombok.Data;

@Data
public class CurrentUserEnterprise {
    private Long enterpriseId;
    private String enterpriseName;
    private Boolean isCurrent;
}
