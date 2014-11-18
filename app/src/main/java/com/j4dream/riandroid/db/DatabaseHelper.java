package com.j4dream.riandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dream on 2014/11/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydata.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTable = "create table user(_id varchar(36), username varchar(20) not null , password varchar(60) not null );";
        String monye_used_detail = "create table monye_used_detail(_id varchar(36)," +
                " money double not null ," +
                " type varchar(20)," +
                "time long );";
        db.execSQL(userTable);
        db.execSQL(monye_used_detail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}