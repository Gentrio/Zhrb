package com.gentrio.zhrb.service;

import com.gentrio.zhrb.bean.LatestBean;
import com.gentrio.zhrb.bean.NewsBean;
import com.gentrio.zhrb.bean.ThemesBean;
import com.gentrio.zhrb.bean.ThemesMenuBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Gentrio on 2016/10/21.
 * 服务器接口
 */
public interface Service {

    @GET("/api/4/news/latest")
    Call<LatestBean> getLatest();

    @GET("/api/4/news/{id}")
    Call<NewsBean> getNews(@Path("id") String id);

    @GET("/api/4/news/before/{date}")
    Call<LatestBean> getBefore(@Path("date") String date);

    @GET("/api/4/themes")
    Call<ThemesMenuBean> getThemesMenu();

    @GET("/api/4/theme/{id}")
    Call<ThemesBean> getThemes(@Path("id") int id);

}
