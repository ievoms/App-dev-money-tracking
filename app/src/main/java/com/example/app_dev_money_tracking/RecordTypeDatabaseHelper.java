package com.example.app_dev_money_tracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RecordTypeDatabaseHelper extends SQLiteOpenHelper {
    public static final String RECORD_TYPE_TABLE = "RECORD_TYPE_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";

    public RecordTypeDatabaseHelper(@Nullable Context context) {
        super(context, "moneyApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + RECORD_TYPE_TABLE +
                " (" + COLUMN_ID + " CHAR(1) PRIMARY KEY NOT NULL, " + COLUMN_NAME + " TEXT );";
        String insertIncome = " INSERT INTO" + RECORD_TYPE_TABLE + " (" + COLUMN_ID + "," + COLUMN_NAME + ") VALUES(I , Income);";
        String insertExpense = " INSERT INTO" + RECORD_TYPE_TABLE + " (" + COLUMN_ID + "," + COLUMN_NAME + ") VALUES(E , Expense )";


        db.execSQL(createTableStatement);
        addRecordType("I","Income");
        addRecordType("E","Expense");
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_ID, "I");
//        contentValues.put(COLUMN_NAME, "Income");
//        db.insert(RECORD_TYPE_TABLE, null, contentValues);
//        contentValues.put(COLUMN_ID, "E");
//        contentValues.put(COLUMN_NAME, "Expense");
//        db.insert(RECORD_TYPE_TABLE, null, contentValues);


    }
    public void addRecordType(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME, name);
        db.insert(RECORD_TYPE_TABLE, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
