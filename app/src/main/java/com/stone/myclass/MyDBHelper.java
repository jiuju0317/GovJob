package com.stone.myclass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by meamea on 2015/6/12.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    // 資料庫名稱
    public static final String DATABASE_NAME = "jobdb.sqlite";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        //database.execSQL(JobDAO.CREATE_FAVORITE);
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JobDAO.CREATE_TABLE);
        db.execSQL(JobDAO.CREATE_ANNOTABLE);
        db.execSQL(JobDAO.CREATE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的表格
        db.execSQL("DROP TABLE IF EXISTS " + JobDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JobDAO.TABLE_NAME_ANNO);
        db.execSQL("DROP TABLE IF EXISTS " + JobDAO.TABLE_NAME_FAVORITE);
        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }
}
