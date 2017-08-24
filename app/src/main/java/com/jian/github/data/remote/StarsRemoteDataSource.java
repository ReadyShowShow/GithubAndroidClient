package com.jian.github.data.remote;

import com.jian.github.bean.apibean.Repository;
import com.jian.github.data.IStarsDataSource;
import com.jian.github.network.Api;

import java.util.List;

import rx.Observable;

public class StarsRemoteDataSource implements IStarsDataSource {
    @Override
    public Observable<List<Repository>> queryMineStars(int page, int limit) {
        return Api.getUserApi().getStarred(page, limit);
    }

    @Override
    public Observable<List<Repository>> querySomeoneStars(String username, int page, int limit) {
        return Api.getUserApi().getStarred(username, page, limit);
    }
}
