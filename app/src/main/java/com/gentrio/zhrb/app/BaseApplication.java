package com.gentrio.zhrb.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.gentrio.zhrb.service.CacheInterceptor;
import com.gentrio.zhrb.service.ImageDownLoader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Created by Gentrio on 2016/10/20.
 */
public class BaseApplication extends Application {

    private static String cachePath;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cachePath = getApplicationContext().getExternalCacheDir().getAbsolutePath();
        }else {
            cachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        }
        context = getApplicationContext();
        initPicasso();
    }

    private void initPicasso() {
        File cacheFile = new File(BaseApplication.getCachePath()+"/images/");
        okhttp3.Cache cache = new okhttp3.Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CacheInterceptor(context))
                .addInterceptor(new CacheInterceptor(context))
                .build();
        Picasso.setSingletonInstance(new Picasso.Builder(getApplicationContext()).downloader(new ImageDownLoader(client)).build());
    }

    public static String getCachePath(){
        return cachePath;
    }

    public static Context getContext() {
        return context;
    }
}
