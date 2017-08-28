package com.jian.github.module.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.jian.github.App;
import com.jian.github.R;
import com.jian.github.base.BaseFragment;
import com.jian.github.base.BasePresenter;
import com.jian.github.data.remote.LoginDataSource;
import com.jian.github.utils.DialogUtils;
import com.jian.github.utils.Log;
import com.jian.github.utils.RxJavaUtils;
import com.jian.github.utils.ScreenUtils;
import com.jian.github.view.InputEdit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Description：登录
 */
public class LoginFragment extends BaseFragment {

    @BindView(R.id.input_login_username)
    InputEdit mInputUsername;
    @BindView(R.id.input_login_password)
    InputEdit mInputPassword;
    private ProgressDialog mProgressDialog;

    @Override
    protected Status getCurrentStatus() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View parent) {
        requestNoToolbar();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.btn_login, R.id.tv_login_just_browsing})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: {
                String username = mInputUsername.getInputText().trim();
                String password = mInputPassword.getInputText().trim();
                if (TextUtils.isEmpty(username)) {
                    toast(R.string.error_username_null);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    toast(R.string.error_password_null);
                    return;
                }
                RxJavaUtils.wrap(LoginDataSource.login(username, password))
                        .doOnSubscribe(() -> {
                            Context context = getContext();
                            mProgressDialog =
                                    DialogUtils.showProgressDialog(context, context.getString(R.string.loading),
                                            false);
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                            DialogUtils.dismissProgressDialog(mProgressDialog);
                            App.setAuthorization(username, password);
                            App.onLogin(user);
                            finishSelf();
                        }, error -> {
                            DialogUtils.dismissProgressDialog(mProgressDialog);
                            Log.e(TAG, "登录失败：", error);
                            toast(R.string.error_login_failure);
                        });
                break;
            }
            case R.id.tv_login_just_browsing: {
                showInputUsernameDialog(view -> finishSelf());
                break;
            }
        }
    }

    private void finishSelf() {
        FragmentActivity activity = getActivity();
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }

    /**
     * 显示输入用户名的dialog
     *
     * @param onConfirmClick 点确定的回调
     */
    private void showInputUsernameDialog(View.OnClickListener onConfirmClick) {
        AlertDialog.Builder builder = DialogUtils.getAlertDialog(getContext());
        AlertDialog dialog = builder.setView(R.layout.dialog_input_view).create();
        dialog.show();
        TextView tvTitle = ButterKnife.findById(dialog, R.id.tv_dialog_input_title);
        tvTitle.setText(R.string.username_github);
        EditText etUsername = ButterKnife.findById(dialog, R.id.et_dialog_input);
        String user = App.getUsername();
        etUsername.setText(user);
        if (!TextUtils.isEmpty(user)) {
            etUsername.setSelection(user.length());
        }
        Button btnConfirm = ButterKnife.findById(dialog, R.id.btn_dialog_input_confirm);
        btnConfirm.setTag(dialog);
        btnConfirm.setOnClickListener(v -> {
            ScreenUtils.hideKeyBoard(v);
            dialog.dismiss();
            String username = etUsername.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                toast(R.string.error_username_null);
                return;
            }
            App.setUsername(username);
            if (onConfirmClick != null) onConfirmClick.onClick(v);
        });
        Button btnCancel = ButterKnife.findById(dialog, R.id.btn_dialog_input_cancel);
        btnCancel.setTag(dialog);
        btnCancel.setOnClickListener(v -> {
            ScreenUtils.hideKeyBoard(v);
            dialog.dismiss();
        });
    }
}
