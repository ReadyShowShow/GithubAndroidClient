package com.jian.github.data.remote;

import android.util.Base64;

import com.jian.github.bean.apibean.User;
import com.jian.github.network.Api;

import rx.Observable;

/**
 * Description：登录相关
 */
public class LoginDataSource {
    public static Observable<User> login(String username, String password) {
        String authorization =
                "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
        return Api.getUserApi().login(authorization);
    }
}
