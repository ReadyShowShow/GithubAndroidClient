package com.jian.github.network;

import android.text.TextUtils;

import com.jian.github.App;
import com.ouyangzn.github.BuildConfig;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 匿名 SSL HttpClient
 */
public class HttpClient {
    public static OkHttpClient getDefaultHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        String authorization = App.getAuthorization();
        if (!TextUtils.isEmpty(authorization)) {
            builder.interceptors().add(0, chain -> {
                Request.Builder reqBuilder = chain.request()
                        .newBuilder().addHeader("Authorization", App.getAuthorization());
                return chain.proceed(reqBuilder.build());
            });
        }
        if (BuildConfig.DEBUG) {
            try {
                Class<?> clazz = Class.forName("com.facebook.stetho.okhttp3.StethoInterceptor");
                Interceptor interceptor = (Interceptor) clazz.newInstance();
                builder.addNetworkInterceptor(interceptor);
            } catch (ClassNotFoundException e) {
                // ignore
            } catch (InstantiationException e) {
                // ignore
            } catch (IllegalAccessException e) {
                // ignore
            }
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.interceptors().add(logging);
        }
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                    new java.security.SecureRandom());
            builder.sslSocketFactory(sc.getSocketFactory(), new TrustAnyTrustManager());
            builder.hostnameVerifier(new TrustAnyHostnameVerifier());
        } catch (KeyManagementException ignored) {
        } catch (NoSuchAlgorithmException ignored) {
        }
        return builder.build();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
