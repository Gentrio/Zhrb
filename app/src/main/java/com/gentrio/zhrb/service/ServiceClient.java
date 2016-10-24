package com.gentrio.zhrb.service;

import android.util.Log;

import com.gentrio.zhrb.app.BaseApplication;
import com.gentrio.zhrb.config.API;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gentrio on 2016/10/21.
 */
public class ServiceClient {

    private static final ServiceClient instance = new ServiceClient();
    private Retrofit retrofit;

    private ServiceClient(){
        File cacheFile = new File(BaseApplication.getCachePath()+"/text/");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50M
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new CacheInterceptor(BaseApplication.getContext()))
                .addInterceptor(new CacheInterceptor(BaseApplication.getContext()))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Service getService() {
        return instance.retrofit.create(Service.class);
    }
}
