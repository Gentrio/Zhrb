package com.gentrio.zhrb.service;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gentrio.zhrb.bean.LatestBean;
import com.gentrio.zhrb.utils.DbHelper;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Gentrio on 2016/10/24.
 */
public class DbService {

    private DbHelper dbHelper;

    public DbService(Context context) {
        dbHelper = new DbHelper(context);
    }

    public Observable<List<LatestBean.StoriesBean>> getAll() {
        return Observable.create(new Observable.OnSubscribe<List<LatestBean.StoriesBean>>() {

            @Override
            public void call(Subscriber<? super List<LatestBean.StoriesBean>> subscriber) {
                List<LatestBean.StoriesBean> datas = new ArrayList<>();
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from collection order by _id desc", null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String image = cursor.getString(cursor.getColumnIndex("images"));
                    LatestBean.StoriesBean storiesBean = new LatestBean.StoriesBean();
                    storiesBean.setId(id);
                    storiesBean.setTitle(title);
                    List<String> images = new ArrayList<>();
                    images.add(image);
                    storiesBean.setImages(images);
                    datas.add(storiesBean);
                }
                cursor.close();
                db.close();
                subscriber.onNext(datas);
                subscriber.onCompleted();
            }

        }).subscribeOn(Schedulers.io());
    }

    public boolean getOne(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id from collection where id=?",new String[]{id+""});
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }else{
            return false;
        }
    }

    public boolean insert(int id,String title,String image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("insert into collection(id,title,images) values(?,?,?)", new Object[]{id, title, image});
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("delete from collection where id=?", new Object[]{id});
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
