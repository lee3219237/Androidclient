package com.ftoul.androidclient.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ftoul106 on 2016/12/20 0020.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public MySQLiteHelper(Context context) {

        super(context, "data.db", null, 1);
    }

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table msgs("
                + "id integer primary key,"//消息唯一编号
                + "uid integer,"//用户uid
                + "url varchar,"//消息链接
                + "type integer,"//消息类型
                + "title char(60),"//消息标题
                + "content varchar,"//消息正文
                + "isOpen boolean,"//是否已读
                + "sendTime char(40))");//发送时间
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
