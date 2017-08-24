package com.jian.github.data;

import com.jian.github.bean.apibean.Repository;

import java.util.List;

import rx.Observable;

public interface IStarsDataSource {

    /**
     * 查询我的stars
     *
     * @return Observable<List<Repository>>
     */
    Observable<List<Repository>> queryMineStars(int page, int limit);

    /**
     * 查询某个人的stars
     *
     * @param username 用户名
     * @return Observable<List<Repository>>
     */
    Observable<List<Repository>> querySomeoneStars(String username, int page, int limit);
}
