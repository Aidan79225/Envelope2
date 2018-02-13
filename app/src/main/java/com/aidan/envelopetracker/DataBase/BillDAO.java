package com.aidan.envelopetracker.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aidan.envelopetracker.Model.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2016/10/2.
 */

public class BillDAO {
    public static final String TABLE_NAME = "Bill";

    public static final String ObjectIdColumn = "objectId";
    public static final String NameColumn = "name";
    public static final String CommentColumn = "comment";
    public static final String CostColumn = "cost";
    public static final String DateColumn = "date";
    public static final String EnvelopIdColumn = "envelopId";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ObjectIdColumn + " TEXT PRIMARY KEY, " +
                    NameColumn + " TEXT NOT NULL, " +
                    CommentColumn + " TEXT NOT NULL, " +
                    DateColumn + " INTEGER NOT NULL, " +
                    EnvelopIdColumn + " TEXT NOT NULL, " +
                    CostColumn + " INTEGER NOT NULL)";
    private SQLiteDatabase db;
    private static BillDAO billDAO;

    public static void init(Context context) {
        billDAO = new BillDAO(context);
    }

    public static BillDAO getInstance() {
        if (billDAO == null) throw new NullPointerException("has not init");
        return billDAO;
    }

    private BillDAO(Context context) {
        db = DBHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public Bill insert(Bill item) {
        ContentValues cv = new ContentValues();
        cv.put(NameColumn, item.getEnvelopeName());
        cv.put(CommentColumn, item.getComment());
        cv.put(CostColumn, item.getCost());
        cv.put(ObjectIdColumn, item.getId());
        cv.put(DateColumn, item.getTime());
        cv.put(EnvelopIdColumn, item.getEnvelopId());

        long id = db.insert(TABLE_NAME, null, cv);
        // 回傳結果
        return item;
    }

    // 修改參數指定的物件
    public boolean update(Bill item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(NameColumn, item.getEnvelopeName());
        cv.put(CommentColumn, item.getComment());
        cv.put(CostColumn, item.getCost());
        cv.put(DateColumn, item.getTime());
        cv.put(EnvelopIdColumn, item.getEnvelopId());
        String where = ObjectIdColumn + "= \"" + item.getId() + "\"";
        long test = db.update(TABLE_NAME, cv, where, null);

        return test > 0;
    }

    public boolean delete(String id) {
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = ObjectIdColumn + "= \"" + id + "\"";
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public List<Bill> getAll() {
        List<Bill> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            try {
                result.add(getRecord(cursor));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        cursor.close();
        return result;
    }

    public List<Bill> getEnvelopsAccount(String envelopName) {
        List<Bill> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, EnvelopIdColumn + "= \"" + envelopName + "\"", null, null, null, null, null);
        while (cursor.moveToNext()) {
            try {
                result.add(getRecord(cursor));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public Bill get(String id) {
        // 準備回傳結果用的物件
        Bill item = null;
        // 使用編號為查詢條件
        String where = ObjectIdColumn + "= \"" + id + "\"";
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

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
    public Bill getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Bill result = new Bill();
        result.setId(cursor.getString(0));
        result.setEnvelopeName(cursor.getString(1));
        result.setComment(cursor.getString(2));
        result.setTime(cursor.getLong(3));
        result.setEnvelopId(cursor.getString(4));
        result.setCost(cursor.getInt(5));

        // 回傳結果
        return result;
    }

    public void removeAll() {
        db.delete(TABLE_NAME, null, null);
    }

}
