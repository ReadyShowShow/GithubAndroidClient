package com.jian.github.module.main;

import com.jian.github.base.BasePresenter;
import com.jian.github.base.BaseView;
import com.jian.github.bean.apibean.RepoSearchResult;
import com.jian.github.bean.apibean.Repository;
import com.jian.github.bean.localbean.SearchFactor;

public interface MainContract {

    interface IMainView extends BaseView<IMainPresenter> {

        void showErrorOnQueryData(String tips);

        void showQueryDataResult(RepoSearchResult result);

        void showCollected();

        void showCollectedFailure();
    }

    abstract class IMainPresenter extends BasePresenter<IMainView> {
        /**
         * 搜索github上的数据
         *
         * @param factor 搜索因子（即各种搜索条件）
         */
        public abstract void queryData(SearchFactor factor);

        /**
         * 保存搜索因子
         *
         * @param factor 搜索因子
         */
        public abstract void saveSearchFactor(SearchFactor factor);

        /**
         * 收藏项目
         *
         * @param repo
         */
        public abstract void collectRepo(Repository repo);
    }
}
