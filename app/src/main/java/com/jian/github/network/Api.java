package com.jian.github.network;

import com.jian.github.App;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private Api() {
    }

    public static SearchApi getSearchApi() {
        return getRetrofitBuilder()
                .build()
                .create(SearchApi.class);
    }

    public static UserApi getUserApi() {
        return getRetrofitBuilder().build().create(UserApi.class);
    }

    private static Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder().baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(App.getApp().getGson()))
                .client(HttpClient.getDefaultHttpClient());
    }
}
