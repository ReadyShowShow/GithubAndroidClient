package com.jian.github.module.collect;

import com.jian.github.bean.localbean.CollectedRepo;
import com.jian.github.data.ICollectDataSource;
import com.jian.github.data.local.CollectLocalDataSourceSource;
import com.jian.github.utils.Log;
import com.jian.github.utils.RxJavaUtils;
import com.jian.github.App;
import com.ouyangzn.github.R;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.FragmentEvent;

import rx.Observable;
import rx.Subscription;

public class CollectionsPresenter extends CollectionsContract.ICollectionsPresenter {

    private final String TAG = CollectionsPresenter.class.getSimpleName();

    private ICollectDataSource mCollectData;
    private LifecycleProvider<FragmentEvent> mProvider;

    private Subscription mQueryAllSub;
    private Subscription mQueryByKeySub;

    public CollectionsPresenter(LifecycleProvider<FragmentEvent> provider) {
        mProvider = provider;
        mCollectData = new CollectLocalDataSourceSource();
    }

    @Override
    public void queryByKey(String key, int page, int limit) {
        if (mQueryByKeySub != null && mQueryByKeySub.isUnsubscribed()) mQueryByKeySub.unsubscribe();
        mQueryByKeySub = RxJavaUtils.wrapFragment(
                Observable.defer(() -> Observable.just(mCollectData.queryByKeyword(key, page, limit))),
                mProvider)
                .subscribe(results -> {
                    mView.showCollectQueryByKey(results);
                }, error -> {
                    Log.e(TAG, "----------查询收藏,queryByKey出错：", error);
                    mView.showQueryByKeyFailure(App.getApp().getString(R.string.error_search_failure));
                });
    }

    @Override
    public void queryCollect(int page, int limit) {
        if (mQueryAllSub != null && mQueryAllSub.isUnsubscribed()) {
            mQueryAllSub.unsubscribe();
        }
        mQueryAllSub = RxJavaUtils.wrapFragment(
                Observable.defer(() -> Observable.just(mCollectData.queryCollectRepo(page, limit))),
                mProvider).subscribe(repoList -> mView.showCollect(repoList), error -> {
            Log.e(TAG, "----------查询收藏,queryByKey出错：", error);
            mView.showErrorOnQueryFailure(App.getApp().getString(R.string.error_search_failure));
        });
    }

    @Override
    public void cancelCollectRepo(CollectedRepo repo) {
        RxJavaUtils.wrapFragment(
                Observable.defer(() -> Observable.just(mCollectData.cancelRepo(repo.getId()))),
                mProvider).subscribe(success -> {
            if (success) {
                mView.showCollectionCanceled(repo);
            } else {
                mView.showCollectionCancelFailure();
            }
        }, error -> {
            Log.e(TAG, "取消收藏出错：", error);
            mView.showCollectionCancelFailure();
        });
    }

    @Override
    protected void onDestroy() {
        mCollectData = null;
    }

}
