package com.jian.github.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import static com.jian.github.App.getAuthorization;
import static com.jian.github.App.getUsername;

public class CommonUtils {

    /**
     * 复制文本
     *
     * @param context Context
     * @param content 文本内容
     */
    public static void copy(Context context, String content) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(content, content);
        clip.setPrimaryClip(data);
    }

    /**
     * 是否能编辑 e.g. star、fork...
     *
     * @return true表示可以
     */
    public static boolean canEdit() {
        return !TextUtils.isEmpty(getAuthorization());
    }

    /**
     * 是否能浏览某个用户下的东西
     *
     * @return true表示可以
     */
    public static boolean canBrowsing() {
        return !TextUtils.isEmpty(getAuthorization()) || !TextUtils.isEmpty(getUsername());
    }

}
