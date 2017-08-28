package com.jian.github.module.stars;

import com.jian.github.bean.apibean.Repository;
import com.jian.github.data.IStarsDataSource;
import com.jian.github.data.local.CollectLocalDataSourceSource;
import com.jian.github.utils.CommonUtils;
import com.jian.github.utils.Log;
import com.jian.github.App;
import com.jian.github.R;
import com.jian.github.bean.localbean.CollectedRepo;
import com.jian.github.data.ICollectDataSource;
import com.jian.github.data.remote.StarsRemoteDataSource;
import com.jian.github.utils.RxJavaUtils;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.FragmentEvent;

import java.util.List;

import rx.Observable;

public class StarsPresenter extends StarsContract.IStarsPresenter {

    private static final String TAG = StarsPresenter.class.getSimpleName();

    private LifecycleProvider<FragmentEvent> mProvider;
    private IStarsDataSource mStarsDataSource;
    private ICollectDataSource mCollectDataSource;

    public StarsPresenter(LifecycleProvider<FragmentEvent> provider) {
        mProvider = provider;
        mStarsDataSource = new StarsRemoteDataSource();
        mCollectDataSource = new CollectLocalDataSourceSource();
    }

    @Override
    protected void onDestroy() {
        mStarsDataSource = null;
        mProvider = null;
    }

    @Override
    public void queryMineStars(int page, int limit) {
        Observable<List<Repository>> observable;
        if (CommonUtils.canEdit()) {
            observable = mStarsDataSource.queryMineStars(page, limit);
        } else if (CommonUtils.canBrowsing()) {
            observable = mStarsDataSource.querySomeoneStars(App.getUsername(), page, limit);
        } else {
            mView.showOnQueryStarsFail(App.getApp().getString(R.string.error_need_login));
            return;
        }
        RxJavaUtils.wrapFragment(observable, mProvider)
                .subscribe(result -> mView.showStars(result), error -> {
                    Log.e(TAG, "查询我的star出错：", error);
                    mView.showOnQueryStarsFail(App.getApp().getString(R.string.error_network_error));
                });
    }

    @Override
    public void collectRepo(Repository repo) {
        RxJavaUtils.wrap(Observable.defer(() -> {
            CollectedRepo collectedRepo = new CollectedRepo();
            collectedRepo.convert(repo);
            collectedRepo.setCollectTime(System.currentTimeMillis());
            return Observable.just(mCollectDataSource.collectRepo(collectedRepo));
        })).subscribe(success -> {
            if (success) {
                mView.showCollected();
            } else {
                Log.e(TAG, "----------未知原因收藏失败-----------");
                mView.showCollectedFailure(App.getApp().getString(R.string.error_collect_failure));
            }
        }, error -> {
            Log.e(TAG, "----------收藏失败：", error);
            mView.showCollectedFailure(App.getApp().getString(R.string.error_collect_failure));
        });
    }
}
