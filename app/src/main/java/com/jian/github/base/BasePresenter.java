package com.jian.github.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T> {

    protected T mView;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    public void onAttach(T view) {
        this.mView = view;
    }

    public void onDetach() {
        // 使用rxLifecycle来自由进行取消订阅的控制
        // 先取消订阅，然后再置空view，就不会出现刚好出结果但是view被置空了的情况
        mSubscriptions.clear();
        this.mView = null;
        this.onDestroy();
    }

    /**
     * 对应的view销毁时调用的清理资源方法
     */
    protected abstract void onDestroy();

    /**
     * @param subscription
     * @see <a href="https://github.com/trello/RxLifecycle">RxLifecycle</a>
     * @deprecated 使用rxLifecycle来自由进行取消订阅的控制
     */
    @Deprecated()
    protected void addSubscription(Subscription subscription) {
        this.mSubscriptions.add(subscription);
    }
}
