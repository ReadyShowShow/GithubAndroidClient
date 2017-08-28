package com.jian.github.module.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.jian.github.bean.apibean.Repository;
import com.jian.github.bean.localbean.CollectedRepo;
import com.jian.github.bean.localbean.SearchFactor;
import com.jian.github.data.ICollectDataSource;
import com.jian.github.data.IRepositoryDataSource;
import com.jian.github.data.local.CollectLocalDataSourceSource;
import com.jian.github.utils.Log;
import com.jian.github.utils.RxJavaUtils;
import com.jian.github.App;
import com.jian.github.R;
import com.jian.github.data.remote.RepositoryRemoteDataSource;
import com.jian.github.utils.SpUtils;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.FragmentEvent;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jian.github.utils.SpUtils.KEY_LANGUAGE;

public class MainPresenter extends MainContract.IMainPresenter {

    private final String TAG = MainPresenter.class.getSimpleName();

    private LifecycleProvider<FragmentEvent> mProvider;
    private IRepositoryDataSource mDataSource;
    private ICollectDataSource mCollectData;

    private App mApp;
    private SharedPreferences mConfigSp;
    private Subscription mQueryDataSubscribe;

    public MainPresenter(Context context, LifecycleProvider<FragmentEvent> provider) {
        mProvider = provider;
        mApp = (App) context.getApplicationContext();
        mDataSource = new RepositoryRemoteDataSource();
        mCollectData = new CollectLocalDataSourceSource();
        mConfigSp = SpUtils.getSp(context);
    }

    @Override
    protected void onDestroy() {
        mDataSource = null;
        mCollectData = null;
    }

    @Override
    public void queryData(SearchFactor factor) {
        if (mQueryDataSubscribe != null && !mQueryDataSubscribe.isUnsubscribed()) {
            mQueryDataSubscribe.unsubscribe();
        }
        mQueryDataSubscribe = mDataSource.queryByKeyword(factor, factor.limit, factor.page)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    mView.setLoadingIndicator(true);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                // 使用rxlifecycle，自由指定取消订阅的时间点
                .compose(mProvider.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(searchResult -> {
                    mView.setLoadingIndicator(false);
                    mView.showQueryDataResult(searchResult);
                }, throwable -> {
                    Log.e(TAG, "----------查询数据出错:" + throwable.getMessage());
                    mView.setLoadingIndicator(false);
                    mView.showErrorOnQueryData(mApp.getString(R.string.error_search_failure));
                });
    }

    @Override
    public void saveSearchFactor(SearchFactor factor) {
        Observable.just(factor)
                .observeOn(Schedulers.io())
                .doOnNext(factor1 -> mConfigSp.edit().putString(KEY_LANGUAGE,
                        App.getApp().getGson().toJson(factor1))
                        .apply())
                .subscribe();
    }

    @Override
    public void collectRepo(final Repository repo) {
        RxJavaUtils.wrap(Observable.defer(() -> {
            CollectedRepo collectedRepo = new CollectedRepo();
            collectedRepo.convert(repo);
            collectedRepo.setCollectTime(System.currentTimeMillis());
            return Observable.just(mCollectData.collectRepo(collectedRepo));
        })).subscribe(success -> {
            if (success) {
                mView.showCollected();
            } else {
                Log.e(TAG, "----------未知原因收藏失败-----------");
                mView.showCollectedFailure();
            }
        }, error -> {
            Log.e(TAG, "----------收藏失败：", error);
            mView.showCollectedFailure();
        });
    }

}
