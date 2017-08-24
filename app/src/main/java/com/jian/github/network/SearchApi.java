package com.jian.github.network;

import com.jian.github.bean.apibean.RepoSearchResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApi {

    @GET("/search/repositories")
    Observable<RepoSearchResult> query(@Query("q") String keyword,
                                       @Query("sort") String sort, @Query("order") String order, @Query("per_page") int perPage,
                                       @Query("page") int page);
}
