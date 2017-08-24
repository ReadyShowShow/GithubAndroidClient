package com.jian.github.utils;

import android.content.Context;

/**
 * Description: 保持只有一个Toast实例，避免接二连三的显示多个toast
 */
public class Toast {

    private static android.widget.Toast toast;
    private static Toast mToast;

    private Toast() {
    }

    public synchronized static Toast getInstance(Context context) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(context.getApplicationContext(), null,
                    android.widget.Toast.LENGTH_SHORT);
            mToast = new Toast();
        }
        return mToast;
    }

    /**
     * show a toast
     *
     * @param duration {@link android.widget.Toast#LENGTH_SHORT} 或者  {@link
     *                 android.widget.Toast#LENGTH_LONG}
     * @param text
     */
    public void show(CharSequence text, int duration) {
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * show a toast
     *
     * @param duration {@link android.widget.Toast#LENGTH_SHORT} 或者  {@link
     *                 android.widget.Toast#LENGTH_LONG}
     * @param resId
     */
    public void show(int resId, int duration) {
        toast.setText(resId);
        toast.setDuration(duration);
        toast.show();
    }
}
