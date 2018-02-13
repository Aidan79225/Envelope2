package com.aidan.envelopetracker.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aidan on 2016/10/2.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final static int DBVersion = 4; //<-- 版本
    private final static String DBName = "SaveMoney.db";  //<-- db name
    private static SQLiteDatabase database;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DBHelper(context, DBName,
                    null, DBVersion).getWritableDatabase();
        }

        return database;
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EnvelopeDAO.CREATE_TABLE);
        db.execSQL(BillDAO.CREATE_TABLE);
        db.execSQL(MonthHistoryDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EnvelopeDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BillDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MonthHistoryDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MonthHistoryDAO.envelopeTableName);
        db.execSQL("DROP TABLE IF EXISTS " + MonthHistoryDAO.accountTableName);
        onCreate(db);
    }
}
