package com.dclib.glide;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Glide管理者
 * Created on 2021/8/13
 *
 * @author dc
 */
public class GlideManager {

    private GlideManager() {

    }

    public static GlideManager getInstance() {
        return GlideManagerHolder.INSTANCE;
    }

    private static class GlideManagerHolder {
        private static final GlideManager INSTANCE = new GlideManager();
    }

    /**
     * 初始化glide
     */
    public void init(Application application) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        // 忽略证书
        httpClientBuilder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
        httpClientBuilder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());

        OkHttpClient okHttpClient = httpClientBuilder.build();
        Glide.get(application).getRegistry().replace(GlideUrl.class, InputStream.class
                , new OkHttpUrlLoader.Factory(okHttpClient));
    }
} 