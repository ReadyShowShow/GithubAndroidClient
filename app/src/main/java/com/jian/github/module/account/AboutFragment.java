package com.jian.github.module.account;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

import com.jian.github.base.BaseFragment;
import com.jian.github.utils.Actions;
import com.jian.github.utils.CommonUtils;
import com.jian.github.utils.ImageLoader;
import com.jian.github.R;
import com.jian.github.base.BasePresenter;
import com.jian.github.utils.UiUtils;

/**
 * Description：
 */
public class AboutFragment extends BaseFragment {

    @BindView(R.id.img_about_photo)
    ImageView mImgPhoto;
    @BindView(R.id.tv_about_gmail)
    TextView mTvGmail;
    @BindView(R.id.tv_about_url)
    TextView mTvUrl;

    @Override
    protected Status getCurrentStatus() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_about;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View parent) {
        UiUtils.setCenterTitle(mToolbar, R.string.title_about);
        UiUtils.addWhiteBackBtn(mToolbar, getActivity());
        ImageLoader.loadAsCircle(mImgPhoto, "https://avatars1.githubusercontent.com/u/30887260?v=4&s=460");
        UiUtils.addUnderLine(mTvGmail);
        UiUtils.addUnderLine(mTvUrl);
    }

    @OnClick({R.id.tv_about_gmail, R.id.tv_about_url})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_about_url: {
                Actions.openUrl(getActivity(), mTvUrl.getText().toString());
                break;
            }
            case R.id.tv_about_gmail: {
                CommonUtils.copy(getContext(), mTvGmail.getText().toString());
                toast(R.string.tip_copy_success);
                break;
            }
        }
    }

    @OnLongClick({R.id.tv_about_gmail})
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.tv_about_gmail: {
                CommonUtils.copy(getContext(), mTvGmail.getText().toString());
                toast(R.string.tip_copy_success);
                break;
            }
        }
        return false;
    }
}
