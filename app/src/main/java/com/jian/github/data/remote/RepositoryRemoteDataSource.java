package com.jian.github.data.remote;

import android.text.TextUtils;

import com.jian.github.base.CommonConstants;
import com.jian.github.bean.apibean.RepoSearchResult;
import com.jian.github.bean.localbean.SearchFactor;
import com.jian.github.data.IRepositoryDataSource;
import com.jian.github.network.Api;

import java.net.URLDecoder;

import rx.Observable;

/**
 * <p>eg:</p>
 * <li>no keyword-->https://api.github.com/search/repositories?q=+language:java&sort=stars&per_page=5&page=0</li>
 * <li>has keyword-->https://api.github.com/search/repositories?q=android+language:java&sort=stars&per_page=5&page=0</li>
 * <li>has keyword-->https://api.github.com/search/repositories?q=android+created:>2015-01-09+language:java&sort=stars&order=desc&per_page=3&page=1</li>
 */
public class RepositoryRemoteDataSource implements IRepositoryDataSource {

    @Override
    public Observable<RepoSearchResult> queryByKeyword(SearchFactor factor, String sort, String order,
                                                       int perPage, int page) {
        String q = "";
        if (factor.keyword != null) q = factor.keyword;
        if (!TextUtils.isEmpty(factor.getCreateDate())) {
            // 防止无keyword时，结果搜不到东西
            q = q.concat(URLDecoder.decode("+") + "created:>" + factor.getCreateDate());
        }
        if (!TextUtils.isEmpty(factor.language)) {
            // 防止无keyword时，结果搜不到东西
            q = q.concat(URLDecoder.decode("+") + "language:" + factor.language);
        }
        return Api.getSearchApi().query(q, sort, order, perPage, page);
    }

    @Override
    public Observable<RepoSearchResult> queryByKeyword(SearchFactor factor, int perPage, int page) {
        return queryByKeyword(factor, CommonConstants.GitHub.SORT_STARS, CommonConstants.GitHub.ORDER_DESC, perPage, page);
    }

    @Override
    public Observable<RepoSearchResult> queryByKeyword(SearchFactor factor, int page) {
        return queryByKeyword(factor, CommonConstants.NormalCons.LIMIT_20, page);
    }
}
