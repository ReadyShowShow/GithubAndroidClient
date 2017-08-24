package com.jian.github.base;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;

import com.jian.github.App;
import com.trello.navi.component.support.NaviAppCompatActivity;

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends NaviAppCompatActivity
        implements BaseView<T> {

    protected String TAG = "BaseActivity";
    protected App mApp;
    protected Context mContext;
    protected T mPresenter;
    private com.jian.github.utils.Toast mToast;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        TAG = this.getClass().getSimpleName();
        mApp = (App) getApplication();
        mContext = this;
        mToast = com.jian.github.utils.Toast.getInstance(mContext);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.onAttach((V) this);
        }
        initData();
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 点击界面隐藏软键盘
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    0);
        }
        return super.onTouchEvent(event);
    }

    public abstract T initPresenter();

    protected abstract int getContentView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化界面及控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    protected void toast(String content) {
        mToast.show(content, Toast.LENGTH_SHORT);
    }

    protected void toast(int resId) {
        mToast.show(resId, Toast.LENGTH_SHORT);
    }

    @Override
    public void finish() {
        super.finish();
        //        overridePendingTransition(R.anim.anim_push_in,
        //                R.anim.anim_push_out);
    }

    /**
     * 数据等加载指示器,默认空实现
     *
     * @param isActive 是否正在处理
     */
    @Override
    public void setLoadingIndicator(boolean isActive) {
    }
}
