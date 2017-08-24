package com.jian.github.module.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

import com.jian.github.App;
import com.ouyangzn.github.R;
import com.jian.github.base.BaseFragment;
import com.jian.github.base.BasePresenter;
import com.jian.github.bean.apibean.User;
import com.jian.github.utils.CommonUtils;
import com.jian.github.utils.ImageLoader;
import com.jian.github.utils.UiUtils;

import static com.jian.github.utils.Actions.openUrl;

/**
 * Description：用户信息
 */
public class UserInfoFragment extends BaseFragment {

    public static final String EXTRA_KEY_USER = "user";

    @BindView(R.id.img_user_info_photo)
    ImageView mImgPhoto;
    @BindView(R.id.tv_user_info_name)
    TextView mTvName;
    @BindView(R.id.tv_user_info_email)
    TextView mTvEmail;
    @BindView(R.id.tv_user_info_url)
    TextView mTvUrl;
    @BindView(R.id.btn_user_info_logout)
    Button mBtnLogout;
    private User mUser;

    @Override
    protected Status getCurrentStatus() {
        return null;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUser = getArguments().getParcelable(EXTRA_KEY_USER);
    }

    @Override
    protected void initView(View parent) {
        UiUtils.setCenterTitle(mToolbar, R.string.title_user_info);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        mBtnLogout.setVisibility(mUser.equals(App.getUser()) ? View.VISIBLE : View.GONE);
        ImageLoader.loadAsCircle(mImgPhoto, R.drawable.ic_default_photo, mUser.getAvatarUrl());
        mTvName.setText(mUser.getAuthorName());
        mTvEmail.setText(mUser.getEmail());
        UiUtils.addUnderLine(mTvEmail);
        mTvUrl.setText(mUser.getHtmlUrl());
        UiUtils.addUnderLine(mTvUrl);
    }

    @OnClick({R.id.btn_user_info_logout, R.id.tv_user_info_url, R.id.tv_user_info_email})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_info_url: {
                openUrl(getActivity(), mUser.getHtmlUrl());
                break;
            }
            case R.id.btn_user_info_logout: {
                App.onLogout();
                getActivity().onBackPressed();
                break;
            }
            case R.id.tv_user_info_email: {
                String email = mUser.getEmail();
                if (!TextUtils.isEmpty(email)) {
                    CommonUtils.copy(getContext(), email);
                    toast(R.string.tip_copy_success);
                }
                break;
            }
        }
    }

    @OnLongClick({R.id.tv_user_info_email})
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_info_email: {
                String email = mUser.getEmail();
                if (!TextUtils.isEmpty(email)) {
                    CommonUtils.copy(getContext(), email);
                    toast(R.string.tip_copy_success);
                }
                break;
            }
        }
        return false;
    }
}
