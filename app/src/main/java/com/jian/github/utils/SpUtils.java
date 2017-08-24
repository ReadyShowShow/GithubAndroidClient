package com.jian.github.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    /**
     * 所选语言
     */
    public static final String KEY_LANGUAGE = "language";
    /**
     * 使用的用户
     */
    public static final String KEY_USERNAME = "username";
    /**
     * 使用账号密码登录的认证证书
     */
    public static final String KEY_AUTHORIZATION = "authorization";
    /**
     * 当前登录的用户
     */
    public static final String KEY_USER = "user";
    private static final String SP_NAME = "config";

    public static void put(Context context, String key, String value) {
        getEditor(context).putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String def) {
        return getSp(context).getString(key, def);
    }

    public static void put(Context context, String key, int value) {
        getEditor(context).putInt(key, value).apply();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int def) {
        return getSp(context).getInt(key, def);
    }

    public static void put(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        return getSp(context).getBoolean(key, def);
    }

    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
    }
}
