package com.example.administrator.myapplication.Search;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Administrator on 2016/3/20.
 */
public class CityDatabase extends SQLiteOpenHelper{

//    //建表格式1
//    public static final String CITY_LIST ="create table CITY_LIST("
//    +"id integer primary key autoincrement,"
//    +"city text,"
//    +"num text,"
//
//
//    public CityDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//
//        db.execSQL(CITY_LIST);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }

     //建表格式2
//    private String create=" create  table  if  not  exists  CITY_LIST ( " +
//            "city varchar , cnty varchar , num varchar , lat varchar , lon varchar , prov varchar )";
    private String create=" create  table  if  not  exists  CITY_LIST ( " + "city varchar  , num varchar )";
    public CityDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
        Log.d("=====数据库","＝创＝＝＝＝创建");
    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }

}
