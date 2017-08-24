package com.jian.github.utils;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxJavaUtils {

    private static final String TAG = RxJavaUtils.class.getSimpleName();

    /**
     * 包裹成subscribeOn子线程，observeOn主线程
     *
     * @param observable Observable
     * @param <T>        T
     * @return Observable<T>
     */
    public static <T> Observable<T> wrap(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 包裹成subscribeOn子线程，observeOn主线程, activity销毁时取消
     *
     * @param observable Observable
     * @param <T>        T
     * @param provider   LifecycleProvider<ActivityEvent>
     * @return Observable<T>
     */
    public static <T> Observable<T> wrapActivity(Observable<T> observable,
                                                 LifecycleProvider<ActivityEvent> provider) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * 包裹成subscribeOn子线程，observeOn主线程, activity销毁时取消
     *
     * @param observable Observable
     * @param <T>        T
     * @param provider   LifecycleProvider<FragmentEvent>
     * @return Observable<T>
     */
    public static <T> Observable<T> wrapFragment(Observable<T> observable,
                                                 LifecycleProvider<FragmentEvent> provider) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindUntilEvent(FragmentEvent.DESTROY));
    }

    /**
     * unsubscribe the subscription
     *
     * @param subscription
     */
    public static void unsubscribe(Subscription subscription) {
        if (!isUnsubscribed(subscription)) {
            subscription.unsubscribe();
        }
    }

    /**
     * check if subscription is unsubscribed
     *
     * @param subscription
     */
    public static boolean isUnsubscribed(Subscription subscription) {
        return null == subscription || subscription.isUnsubscribed();
    }

    public static <T> Action1<T> discardResult() {
        return t -> Log.w(TAG,
                "----------discardResult, result = " + (t == null ? "null" : t.toString()));
    }

    public static Action1<Throwable> discardError() {
        return e -> Log.w(TAG, "----------discardError: ", e);
    }
}
