package com.jian.github.network;

import com.jian.github.bean.apibean.Repository;
import com.jian.github.bean.apibean.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Description：用户相关API
 */
public interface UserApi {

    @GET("users/{user}/starred")
    Observable<List<Repository>> getStarred(
            @Path("user") String username, @Query("page") int page, @Query("per_page") int perPage);

    @GET("user/starred")
    Observable<List<Repository>> getStarred(@Query("page") int page,
                                            @Query("per_page") int perPage);

    /**
     * 登录
     *
     * @param authorization 字符串格式："Basic " + Base64.encodeToString((username + ":" +
     *                      password).getBytes(), Base64.NO_WRAP);
     * @return 登录用户的用户信息
     */
    @GET("user")
    Observable<User> login(@Header("Authorization") String authorization);
}
