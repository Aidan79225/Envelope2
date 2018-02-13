package com.aidan.envelopetracker.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aidan.envelopetracker.Model.Envelope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2016/10/2.
 */

public class EnvelopeDAO {
    // 表格名稱
    public static final String TAG = "EnvelopeDAO";
    public static final String TABLE_NAME = "Envelope";

    // 編號表格欄位名稱，固定不變
    public static final String KeyID = "id";

    // 其它表格欄位名稱
    public static final String NameColumn = "name";
    public static final String MaxColumn = "max";
    public static final String CostColumn = "cost";
    public static final String ObjectIdColumn = "objectId";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KeyID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NameColumn + " TEXT NOT NULL, " +
                    MaxColumn + " INTEGER NOT NULL, " +
                    ObjectIdColumn + " TEXT NOT NULL, " +
                    CostColumn + " TEXT NOT NULL)";
    private SQLiteDatabase db;
    private static EnvelopeDAO envelopeDAO;

    public static void init(Context context) {
        Singleton.log("EnvelopeDAO init");
        envelopeDAO = new EnvelopeDAO(context);
    }

    public static EnvelopeDAO getInstance() {
        if (envelopeDAO == null) return null;
        return envelopeDAO;
    }

    private EnvelopeDAO(Context context) {
        db = DBHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public Envelope insert(Envelope item) {
        return insert(item, TABLE_NAME);
    }

    public Envelope insert(Envelope item, String tableName) {
        // 建立準備新增資料的ContentValues物件
        Singleton.log("EnvelopeDAO insert");
        ContentValues cv = new ContentValues();
        cv.put(NameColumn, item.getName());
        cv.put(MaxColumn, item.getMax());
        cv.put(CostColumn, item.getCost());
        cv.put(ObjectIdColumn, item.getId());
        long id = db.insert(tableName, null, cv);

        // 設定編號
        item.setIndex(id);
        // 回傳結果
        return item;
    }

    // 修改參數指定的物件
    public boolean update(Envelope item) {
        return update(item, TABLE_NAME);
    }

    public boolean update(Envelope item, String tableName) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(NameColumn, item.getName());
        cv.put(MaxColumn, item.getMax());
        cv.put(CostColumn, item.getCost());
        cv.put(ObjectIdColumn, item.getId());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KeyID + "=" + item.getIndex();
        long test = db.update(tableName, cv, where, null);
        // 執行修改資料並回傳修改的資料數量是否成功
        Log.e(TAG, test + "");
        return test > 0;
    }

    public boolean delete(long id) {
        return delete(id, TABLE_NAME);
    }

    public boolean delete(long id, String tableNmae) {
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KeyID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(tableNmae, where, null) > 0;
    }

    public void removeAll() {
        removeAll(TABLE_NAME);
    }

    public void removeAll(String tableName) {
        db.delete(tableName, null, null);
    }

    public List<Envelope> getAll() {
        return getAll(TABLE_NAME);
    }

    public List<Envelope> getAll(String tableName) {
        List<Envelope> result = new ArrayList<>();
        Cursor cursor = db.query(
                tableName, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public Envelope get(long id) {

        return get(id, TABLE_NAME);
    }

    public Envelope get(long id, String tableName) {
        // 準備回傳結果用的物件
        Envelope item = null;
        // 使用編號為查詢條件
        String where = KeyID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                tableName, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public static Envelope getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Envelope result = new Envelope();

        result.setIndex(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setMax(cursor.getInt(2));
        result.setId(cursor.getString(3));
        result.setCost(cursor.getInt(4));
        // 回傳結果
        return result;
    }


    public static String getMonthCreateTable(String tableName) {
        return "CREATE TABLE " + tableName + " (" +
                KeyID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NameColumn + " TEXT NOT NULL, " +
                MaxColumn + " INTEGER NOT NULL, " +
                ObjectIdColumn + " TEXT NOT NULL, " +
                CostColumn + " TEXT NOT NULL)";
    }


}
