package com.jian.github;

import android.app.Application;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TypeAdapters;
import com.jian.github.bean.apibean.User;
import com.jian.github.event.Event;
import com.jian.github.event.EventType;
import com.jian.github.json.DoubleAdapter;
import com.jian.github.json.IntegerAdapter;
import com.jian.github.json.LongAdapter;
import com.jian.github.utils.ImageLoader;
import com.ouyangzn.github.BuildConfig;
import com.jian.github.db.DaoHelper;
import com.ouyangzn.github.event.EventBusIndex;
import com.jian.github.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import static com.jian.github.utils.SpUtils.KEY_AUTHORIZATION;
import static com.jian.github.utils.SpUtils.KEY_USER;
import static com.jian.github.utils.SpUtils.KEY_USERNAME;

public class App extends Application {

    private static App sApp;
    private static String sUsername;
    private static String sAuthorization;
    private static User sUser;
    private Gson mGson;

    public static App getApp() {
        return sApp;
    }

    public static String getUsername() {
        if (TextUtils.isEmpty(sUsername)) {
            sUsername = SpUtils.getString(sApp, KEY_USERNAME);
        }
        return sUsername;
    }

    public static void setUsername(String username) {
        App.sUsername = username;
        SpUtils.put(sApp, KEY_USERNAME, username);
    }

    public static void onLogin(User user) {
        setUser(user);
        EventBus.getDefault().post(new Event(EventType.TYPE_LOGIN));
    }

    public static void onLogout() {
        clearAuthorization();
        setUser(null);
        EventBus.getDefault().post(new Event(EventType.TYPE_LOGOUT));
    }

    public static User getUser() {
        if (sUser == null) {
            sUser = sApp.getGson().fromJson(SpUtils.getString(sApp, KEY_USER), User.class);
        }
        return sUser;
    }

    public static void setUser(User user) {
        sUser = user;
        SpUtils.put(sApp, KEY_USER, sApp.getGson().toJson(user));
    }

    public static String getAuthorization() {
        if (sAuthorization == null) {
            sAuthorization = SpUtils.getString(sApp, KEY_AUTHORIZATION);
        }
        return sAuthorization;
    }

    public static void setAuthorization(String username, String password) {
        sAuthorization =
                "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
        SpUtils.put(sApp, KEY_AUTHORIZATION, sAuthorization);
    }

    private static void clearAuthorization() {
        sAuthorization = null;
        SpUtils.put(sApp, KEY_AUTHORIZATION, null);
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
            mGson = new GsonBuilder().registerTypeAdapterFactory(
                    TypeAdapters.newFactory(int.class, Integer.class, new IntegerAdapter()))
                    .registerTypeAdapterFactory(
                            TypeAdapters.newFactory(double.class, Double.class, new DoubleAdapter()))
                    .registerTypeAdapterFactory(
                            TypeAdapters.newFactory(long.class, Long.class, new LongAdapter()))
                    .create();
        }
        return mGson;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        ImageLoader.init(sApp);
        DaoHelper.initDao(sApp);
        EventBus.builder()
                .addIndex(new EventBusIndex())
                .logNoSubscriberMessages(BuildConfig.DEBUG)
                .throwSubscriberException(BuildConfig.DEBUG)
                .installDefaultEventBus();
    }

}
