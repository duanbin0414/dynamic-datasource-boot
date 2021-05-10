package com.deven.boot.datasource.holder;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

/**
 * Created by Gavin on 2017/6/20.
 * Update by Joshua on 2017-7-21.
 */
public class UserHolder {

    private static final String algorithmName = "sha1";

    private static final int hashIterations = 1;

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static CurrentUser getUser() {

        CurrentUser currentUser = new CurrentUser();

        String userStr = contextHolder.get();

        if (!Strings.isNullOrEmpty(userStr)) {
            currentUser = JSON.parseObject(userStr, CurrentUser.class);
        }

        return currentUser;
    }

    public static void setUser(String user) {
        contextHolder.set(user);
    }
}


