package com.jian.github.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.jian.github.R;

/**
 * dialog工具类
 */
public class DialogUtils {

    private DialogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 显示一个进度圈
     *
     * @param context
     * @param message    对话框中的信息
     * @param cancelable 对话框能否取消
     * @return ProgressDialog
     */
    public static synchronized ProgressDialog showProgressDialog(Context context, String message,
                                                                 boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        // show需要在setContentView之前，否则会报requestFeature() must be called before adding content
        dialog.show();
        //        dialog.setContentView(R.layout.progress_dialog);
        //        ((TextView)dialog.findViewById(R.id.tv_progressbar_tips)).setText(Message);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        return dialog;
    }

    /**
     * 隐藏进度对话框
     */
    public static synchronized void dismissProgressDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 取消进度对话框,cancel时会回调OnCancelListener中的onCancel()
     */
    public static synchronized void cancelProgressDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    /**
     * 获取一个对话框AlertDialog
     *
     * @param context
     */
    public static synchronized AlertDialog.Builder getAlertDialog(Context context) {
        return new AlertDialog.Builder(context, R.style.BaseAlertDialog);
    }
}
