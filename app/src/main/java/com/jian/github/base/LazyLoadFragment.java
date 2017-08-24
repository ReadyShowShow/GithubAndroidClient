package com.jian.github.base;

import android.view.View;

/**
 * Description：fragment懒加载时继承的基类
 */
public abstract class LazyLoadFragment<V extends BaseView, T extends BasePresenter<V>>
        extends BaseFragment<V, T> {

    private boolean mIsVisible;
    private boolean mIsViewLoaded;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (mIsVisible && !mIsViewLoaded && mContentView != null) {
            lazyInitView(mContentView);
            mIsViewLoaded = true;
        }
    }

    @Override
    protected final void initView(View parent) {
        if (!mIsViewLoaded && mIsVisible && mContentView != null) {
            lazyInitView(parent);
            mIsViewLoaded = true;
        }
    }

    protected abstract void lazyInitView(View parent);

    @Override
    protected Status getCurrentStatus() {
        return Status.STATUS_LOADING;
    }
}
