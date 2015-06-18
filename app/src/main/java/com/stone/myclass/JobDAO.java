package com.stone.myclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meamea on 2015/6/12.
 */
public class JobDAO  {
    //Text shorten length
    int textLength = 20;

    // 表格名稱
    public static final String TABLE_NAME = "job";
    public static final String TABLE_NAME_ANNO = "dbannodate";
    public static final String TABLE_NAME_FAVORITE = "favorite";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String COLUMN_ORG_NAME = "ORG_NAME";
    public static final String COLUMN_PERSON_KIND = "PERSON_KIND";
    public static final String COLUMN_RANK = "RANK";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_SYSNAM = "SYSNAM";
    public static final String COLUMN_NUMBER_OF = "NUMBER_OF";
    public static final String COLUMN_GENDER_TYPE = "GENDER_TYPE";
    public static final String COLUMN_WORK_PLACE_TYPE = "WORK_PLACE_TYPE";
    public static final String COLUMN_DATE_FROM = "DATE_FROM";
    public static final String COLUMN_DATE_TO = "DATE_TO";
    public static final String COLUMN_IS_HANDICAP = "IS_HANDICAP";
    public static final String COLUMN_IS_ORIGINAL = "IS_ORIGINAL";
    public static final String COLUMN_IS_LOCAL_ORIGINAL = "IS_LOCAL_ORIGINAL";
    public static final String COLUMN_IS_TRANING = "IS_TRANING";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_VITAE_EMAIL = "VITAE_EMAIL";
    public static final String COLUMN_WORK_QUALITY = "WORK_QUALITY";
    public static final String COLUMN_WORK_ITEM = "WORK_ITEM";
    public static final String COLUMN_WORK_ADDRESS = "WORK_ADDRESS";
    public static final String COLUMN_CONTACT_METHOD = "CONTACT_METHOD";
    public static final String COLUMN_URL_LINK = "URL_LINK";
    public static final String COLUMN_VIEW_URL = "VIEW_URL";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY UNIQUE, " +
                    COLUMN_ORG_NAME+" TEXT,"+
                    COLUMN_PERSON_KIND+" TEXT,"+
                    COLUMN_RANK+" TEXT,"+
                    COLUMN_TITLE+" TEXT,"+
                    COLUMN_SYSNAM+" TEXT,"+
                    COLUMN_NUMBER_OF+" TEXT,"+
                    COLUMN_GENDER_TYPE+" TEXT,"+
                    COLUMN_WORK_PLACE_TYPE+" TEXT,"+
                    COLUMN_DATE_FROM+" DATETIME,"+
                    COLUMN_DATE_TO+" DATETIME,"+
                    COLUMN_IS_HANDICAP+" BOOL,"+
                    COLUMN_IS_ORIGINAL+" BOOL,"+
                    COLUMN_IS_LOCAL_ORIGINAL+" BOOL,"+
                    COLUMN_IS_TRANING+" BOOL,"+
                    COLUMN_TYPE+" TEXT,"+
                    COLUMN_VITAE_EMAIL+" TEXT,"+
                    COLUMN_WORK_QUALITY+" TEXT,"+
                    COLUMN_WORK_ITEM+" TEXT,"+
                    COLUMN_WORK_ADDRESS+" TEXT,"+
                    COLUMN_CONTACT_METHOD+" TEXT,"+
                    COLUMN_URL_LINK+" TEXT,"+
                    COLUMN_VIEW_URL+" TEXT"+
                    ");";
    public static final String CREATE_ANNOTABLE =
            "CREATE TABLE "+TABLE_NAME_ANNO+"(ANNOUNCE_DATE INTEGER);" ;


    // 我的最愛
    public static final String CREATE_FAVORITE =
            "CREATE TABLE " + TABLE_NAME_FAVORITE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY UNIQUE, " +
                    COLUMN_ORG_NAME+" TEXT,"+
                    COLUMN_PERSON_KIND+" TEXT,"+
                    COLUMN_RANK+" TEXT,"+
                    COLUMN_TITLE+" TEXT,"+
                    COLUMN_SYSNAM+" TEXT,"+
                    COLUMN_NUMBER_OF+" TEXT,"+
                    COLUMN_GENDER_TYPE+" TEXT,"+
                    COLUMN_WORK_PLACE_TYPE+" TEXT,"+
                    COLUMN_DATE_FROM+" DATETIME,"+
                    COLUMN_DATE_TO+" DATETIME,"+
                    COLUMN_IS_HANDICAP+" BOOL,"+
                    COLUMN_IS_ORIGINAL+" BOOL,"+
                    COLUMN_IS_LOCAL_ORIGINAL+" BOOL,"+
                    COLUMN_IS_TRANING+" BOOL,"+
                    COLUMN_TYPE+" TEXT,"+
                    COLUMN_VITAE_EMAIL+" TEXT,"+
                    COLUMN_WORK_QUALITY+" TEXT,"+
                    COLUMN_WORK_ITEM+" TEXT,"+
                    COLUMN_WORK_ADDRESS+" TEXT,"+
                    COLUMN_CONTACT_METHOD+" TEXT,"+
                    COLUMN_URL_LINK+" TEXT,"+
                    COLUMN_VIEW_URL+" TEXT"+
                    ");";


    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public JobDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public Job insert(Job job) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(KEY_ID, job.get_id());
        cv.put(COLUMN_ORG_NAME,job.getOrgName());
        cv.put(COLUMN_PERSON_KIND,job.getPersonKind());
        cv.put(COLUMN_RANK,job.getRank());
        cv.put(COLUMN_TITLE,job.getTitle());
        cv.put(COLUMN_SYSNAM,job.getSysnam());
        cv.put(COLUMN_NUMBER_OF,job.getNumberOf());
        cv.put(COLUMN_GENDER_TYPE,job.getGenderType());
        cv.put(COLUMN_WORK_PLACE_TYPE,job.getWorkPlaceType());
        cv.put(COLUMN_DATE_FROM, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(job.getDateFrom()));
        cv.put(COLUMN_DATE_TO, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(job.getDateTo()));
        cv.put(COLUMN_IS_HANDICAP,job.isHandicap());
        cv.put(COLUMN_IS_ORIGINAL,job.isOriginal());
        cv.put(COLUMN_IS_LOCAL_ORIGINAL,job.isLocalOriginal());
        cv.put(COLUMN_IS_TRANING,job.isTraning());
        cv.put(COLUMN_TYPE,job.getType());
        cv.put(COLUMN_VITAE_EMAIL,job.getVitaeEmail());
        cv.put(COLUMN_WORK_QUALITY,job.getWorkQuality());
        cv.put(COLUMN_WORK_ITEM,job.getWorkItem());
        cv.put(COLUMN_WORK_ADDRESS,job.getWorkAddress());
        cv.put(COLUMN_CONTACT_METHOD,job.getContactMethod());
        cv.put(COLUMN_URL_LINK,job.getUrlLink());
        cv.put(COLUMN_VIEW_URL,job.getViewUrl());

        // 新增一筆資料
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        db. insertWithOnConflict(TABLE_NAME, null, cv,SQLiteDatabase.CONFLICT_IGNORE);

        // 回傳結果
        return job;
    }

    // 修改參數指定的物件
    public boolean update(Job job) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(KEY_ID, job.get_id());
        cv.put(COLUMN_ORG_NAME,job.getOrgName());
        cv.put(COLUMN_PERSON_KIND,job.getPersonKind());
        cv.put(COLUMN_RANK,job.getRank());
        cv.put(COLUMN_TITLE,job.getTitle());
        cv.put(COLUMN_SYSNAM,job.getSysnam());
        cv.put(COLUMN_NUMBER_OF,job.getNumberOf());
        cv.put(COLUMN_GENDER_TYPE,job.getGenderType());
        cv.put(COLUMN_WORK_PLACE_TYPE,job.getWorkPlaceType());
        cv.put(COLUMN_DATE_FROM, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(job.getDateFrom()));
        cv.put(COLUMN_DATE_TO, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(job.getDateTo()));
        cv.put(COLUMN_IS_HANDICAP,job.isHandicap());
        cv.put(COLUMN_IS_ORIGINAL,job.isOriginal());
        cv.put(COLUMN_IS_LOCAL_ORIGINAL,job.isLocalOriginal());
        cv.put(COLUMN_IS_TRANING,job.isTraning());
        cv.put(COLUMN_TYPE,job.getType());
        cv.put(COLUMN_VITAE_EMAIL,job.getVitaeEmail());
        cv.put(COLUMN_WORK_QUALITY,job.getWorkQuality());
        cv.put(COLUMN_WORK_ITEM,job.getWorkItem());
        cv.put(COLUMN_WORK_ADDRESS,job.getWorkAddress());
        cv.put(COLUMN_CONTACT_METHOD,job.getContactMethod());
        cv.put(COLUMN_URL_LINK,job.getUrlLink());
        cv.put(COLUMN_VIEW_URL,job.getViewUrl());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + job.get_id();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(int id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 新增參數指定的物件
    public Job insertFavorite(Job job) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(KEY_ID, job.get_id());
        cv.put(COLUMN_ORG_NAME,job.getOrgName());
        cv.put(COLUMN_PERSON_KIND,job.getPersonKind());
        cv.put(COLUMN_RANK,job.getRank());
        cv.put(COLUMN_TITLE,job.getTitle());
        cv.put(COLUMN_SYSNAM,job.getSysnam());
        cv.put(COLUMN_NUMBER_OF,job.getNumberOf());
        cv.put(COLUMN_GENDER_TYPE,job.getGenderType());
        cv.put(COLUMN_WORK_PLACE_TYPE,job.getWorkPlaceType());
        cv.put(COLUMN_DATE_FROM, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(job.getDateFrom()));
        cv.put(COLUMN_DATE_TO, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(job.getDateTo()));
        cv.put(COLUMN_IS_HANDICAP,job.isHandicap());
        cv.put(COLUMN_IS_ORIGINAL,job.isOriginal());
        cv.put(COLUMN_IS_LOCAL_ORIGINAL,job.isLocalOriginal());
        cv.put(COLUMN_IS_TRANING,job.isTraning());
        cv.put(COLUMN_TYPE,job.getType());
        cv.put(COLUMN_VITAE_EMAIL,job.getVitaeEmail());
        cv.put(COLUMN_WORK_QUALITY,job.getWorkQuality());
        cv.put(COLUMN_WORK_ITEM,job.getWorkItem());
        cv.put(COLUMN_WORK_ADDRESS,job.getWorkAddress());
        cv.put(COLUMN_CONTACT_METHOD,job.getContactMethod());
        cv.put(COLUMN_URL_LINK,job.getUrlLink());
        cv.put(COLUMN_VIEW_URL,job.getViewUrl());

        // 新增一筆資料
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        db. insertWithOnConflict(TABLE_NAME_FAVORITE, null, cv,SQLiteDatabase.CONFLICT_IGNORE);

        // 回傳結果
        return job;
    }

    // 刪除參數指定編號的資料
    public boolean deleteFavorite(int id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME_FAVORITE, where , null) > 0;
    }

    // 讀取所有記事資料
    public ArrayList<Job> queryAllFavorite() {
        ArrayList<Job> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_FAVORITE, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor,-1));
        }

        cursor.close();
        return result;
    }

    // 讀取所有記事資料
    public ArrayList<Job> getAll() {
        ArrayList<Job> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor,-1));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public Job queryJob(int id) {
        // 準備回傳結果用的物件
        Job job = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            job = getRecord(result,-1);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return job;
    }

    // 取得符合查詢條件的資料物件
    public ArrayList<Job> queryJob(String where) {
        where = "SELECT * FROM "+TABLE_NAME+ " WHERE " + where + ";";
        Log.i("where=>",where);
        ArrayList<Job> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(where, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor,textLength));
        }

        cursor.close();
        return result;
    }

    // 把Cursor目前的資料包裝為物件
    public Job getRecord(Cursor cursor,int textLength) {
        String tempWorkQuality="",tempWorkItem="",tempContactMethod="",tempWorkPlaceType="";

        // 準備回傳結果用的物件
        Job result = new Job();

        result.set_id(cursor.getInt(0));
        result.setOrgName(cursor.getString(1));
        result.setPersonKind(cursor.getString(2));
        result.setRank(cursor.getString(3));
        result.setTitle(cursor.getString(4));
        result.setSysnam(cursor.getString(5));
        result.setNumberOf(cursor.getString(6));
        result.setGenderType(cursor.getString(7));

        if(cursor.getString(8) != null) {
            tempWorkPlaceType = cursor.getString(8).substring(1);
        }
        result.setWorkPlaceType(tempWorkPlaceType);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateFrom = null,dateTo = null;
        try {
            dateFrom = df.parse(cursor.getString(9));
            dateTo = df.parse(cursor.getString(10));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        result.setDateFrom(dateFrom);
        result.setDateTo(dateTo);

        result.setIsHandicap(cursor.getInt(11) > 0);
        result.setIsOriginal(cursor.getInt(12) > 0);
        result.setIsLocalOriginal(cursor.getInt(13) > 0);
        result.setIsTraning(cursor.getInt(14) > 0);
        result.setType(cursor.getString(15));
        result.setVitaeEmail(cursor.getString(16));

        if(textLength!=-1){
            if(cursor.getString(17) != null) {
                if (cursor.getString(17).length() < textLength) {
                    tempWorkQuality = cursor.getString(17);
                } else {
                    tempWorkQuality = cursor.getString(17).substring(0, textLength) + "...";
                }
            }
            if(cursor.getString(18) != null) {
                if (cursor.getString(18).length() < textLength) {
                    tempWorkItem = cursor.getString(18);
                } else {
                    tempWorkItem = cursor.getString(18).substring(0, textLength) + "...";
                }
            }
            if(cursor.getString(20) != null) {
                if (cursor.getString(20).length() < textLength) {
                    tempContactMethod = cursor.getString(20);
                } else {
                    tempContactMethod = cursor.getString(20).substring(0, textLength) + "...";
                }
            }
        }else{
            tempWorkQuality = cursor.getString(17);
            tempWorkItem = cursor.getString(18);
            tempContactMethod = cursor.getString(20);
        }

        result.setWorkQuality(tempWorkQuality);
        result.setWorkItem(tempWorkItem);
        result.setWorkAddress(cursor.getString(19));
        result.setContactMethod(tempContactMethod);
        result.setUrlLink(cursor.getString(21));
        result.setViewUrl(cursor.getString(22));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount(String where) {
        int result = 0;

        where = "SELECT COUNT(*) FROM "+TABLE_NAME+ " WHERE " + where + ";";

        Cursor cursor = db.rawQuery(where, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    public int getDBAnnoDate(){
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM dbannodate", null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    public boolean setDBAnnoDate(int dbAnnoDate){
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put("ANNOUNCE_DATE", dbAnnoDate);

        return db.update("dbannodate", cv, null, null) > 0;
    }

    public boolean insertDBAnnoDate(int dbAnnoDate){
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put("ANNOUNCE_DATE", dbAnnoDate);
        return db.insert("dbannodate", null, cv)>0;
    }

}
