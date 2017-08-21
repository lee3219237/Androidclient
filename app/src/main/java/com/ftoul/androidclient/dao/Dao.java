package com.ftoul.androidclient.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ftoul106 on 2016/12/20 0020.
 */

public class Dao {
    private SQLiteDatabase db;

    public Dao(Context context) {
        MySQLiteHelper helper = new MySQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public String queryString(String uid, String[] fileds) {
        Cursor cursor = db.query("user_info",fileds, "uid=?", new String[]{uid}, null, null, null);
        String strs = "";
        if (cursor.moveToNext()) {
             strs = cursor.getString(0);
        }
        return strs;
    }

    public int queryInt(String uid, String filed) {
        Cursor cursor = db.query("user_info", new String[]{filed}, "uid=?", new String[]{uid}, null, null, null);
        int position = -1;
        if (cursor.moveToNext()) {
            position = cursor.getInt(0);
        }
       // db.update()
        return position;
    }

    public long insert(ContentValues values){
       return db.insert("user_info",null,values);
    }



}
