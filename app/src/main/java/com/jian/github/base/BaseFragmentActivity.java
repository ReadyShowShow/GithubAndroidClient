package com.jian.github.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.ouyangzn.github.R;
import com.jian.github.utils.Log;
import com.jian.github.utils.ScreenUtils;

public class BaseFragmentActivity extends BaseActivity {

    public static final String DATA_FRAGMENT_NAME = "fragmentName";

    private String mFragmentName;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_base;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFragmentName = savedInstanceState.getString(DATA_FRAGMENT_NAME);
        } else {
            mFragmentName = getIntent().getStringExtra(DATA_FRAGMENT_NAME);
        }
        if (TextUtils.isEmpty(mFragmentName)) {
            Log.w("BaseFragmentActivity", "start activity need a fragment, fragmentName cannot be null");
            finish();
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BaseFragment fragment = newFragment();
        assert fragment != null;
        ft.add(R.id.layout_fragment_container, fragment, getFragmentName()).commit();
    }

    public String getFragmentName() {
        return mFragmentName;
    }

    protected final BaseFragment newFragment() {
        String fragmentName = this.getFragmentName();
        return TextUtils.isEmpty(fragmentName) ? null
                : (BaseFragment) Fragment.instantiate(this, fragmentName, this.getIntent().getExtras());
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        BaseFragment fragment = (BaseFragment) fm.findFragmentByTag(getFragmentName());
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        View focus = getCurrentFocus();
        if (focus != null) ScreenUtils.hideKeyBoard(focus);
        super.finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATA_FRAGMENT_NAME, getFragmentName());
    }
}
