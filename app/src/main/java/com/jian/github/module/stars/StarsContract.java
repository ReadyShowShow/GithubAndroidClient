package com.jian.github.module.stars;

import com.jian.github.base.BasePresenter;
import com.jian.github.base.BaseView;
import com.jian.github.bean.apibean.Repository;

import java.util.List;

public class StarsContract {
    interface IStarsView extends BaseView<IStarsPresenter> {
        void showStars(List<Repository> repoList);

        void showOnQueryStarsFail(String error);

        void showCollected();

        void showCollectedFailure(String error);
    }

    static abstract class IStarsPresenter extends BasePresenter<IStarsView> {

        /**
         * 查询我的stars
         *
         * @param page  页码，从1开始
         * @param limit 每页数量
         */
        public abstract void queryMineStars(int page, int limit);

        /**
         * 收藏项目
         *
         * @param repo
         */
        public abstract void collectRepo(Repository repo);

    }
}
