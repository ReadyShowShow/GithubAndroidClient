package com.jian.github.module.collect;

import com.jian.github.base.BasePresenter;
import com.jian.github.base.BaseView;
import com.jian.github.bean.localbean.CollectedRepo;

import java.util.List;

public class CollectionsContract {

    interface ICollectionsView extends BaseView<ICollectionsPresenter> {

        void showCollect(List<CollectedRepo> repoList);

        void showErrorOnQueryFailure(String error);

        void showCollectQueryByKey(List<CollectedRepo> repoList);

        void showQueryByKeyFailure(String error);

        void showCollectionCanceled(CollectedRepo repo);

        void showCollectionCancelFailure();

    }

    abstract static class ICollectionsPresenter extends BasePresenter<ICollectionsView> {

        /**
         * 查询收藏的项目
         *
         * @param page          当前页
         * @param countEachPage 每页的数量
         */
        public abstract void queryCollect(int page, int countEachPage);

        public abstract void queryByKey(String key, int page, int limit);

        public abstract void cancelCollectRepo(CollectedRepo repo);
    }
}
