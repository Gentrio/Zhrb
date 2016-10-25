package com.gentrio.zhrb.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gentrio on 2016/10/24.
 */
public class DbHelper extends SQLiteOpenHelper {

    private final String TABLE_NAME = "collection";
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(_id integer primary key autoincrement," +
            "id text UNIQUE," +
            "title text," +
            "images text);";

    public DbHelper(Context context) {
        super(context, "collection.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
