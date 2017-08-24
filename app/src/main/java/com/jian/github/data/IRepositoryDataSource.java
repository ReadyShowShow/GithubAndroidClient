package com.jian.github.data;

import com.jian.github.base.CommonConstants.GitHub;
import com.jian.github.bean.apibean.RepoSearchResult;
import com.jian.github.bean.localbean.SearchFactor;

import rx.Observable;

public interface IRepositoryDataSource {
    /**
     * 查询GitHub的数据
     *
     * @param factor  搜索因子（即搜索条件）
     * @param sort    查询的类别 {@link GitHub#SORT_STARS}、{@link GitHub#SORT_FORKS}、{@link
     *                GitHub#SORT_UPDATED}
     * @param order   排序规则 {@link GitHub#ORDER_ASC}、{@link GitHub#ORDER_DESC}
     * @param perPage 每页的数据量
     * @param page    搜索页数
     */
    Observable<RepoSearchResult> queryByKeyword(SearchFactor factor, String sort, String order,
                                                int perPage, int page);

    Observable<RepoSearchResult> queryByKeyword(SearchFactor factor, int perPage, int page);

    Observable<RepoSearchResult> queryByKeyword(SearchFactor factor, int page);

}
