package com.example.app_dev_money_tracking;

import static com.example.app_dev_money_tracking.RecordTypeDatabaseHelper.RECORD_TYPE_TABLE;
import static com.example.app_dev_money_tracking.RecordTypeModel.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordsDatabaseHelper extends SQLiteOpenHelper {
    public static final String RECORDS_TABLE = "RECORDS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";
    public static final String COLUMN_RECORD_TYPE = "RECORD_TYPE";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public RecordsDatabaseHelper(@Nullable Context context) {
        super(context, "moneyApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + RECORDS_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " INTEGER, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_CATEGORY_ID + " INTEGER, " +
                COLUMN_RECORD_TYPE + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    public boolean addRecord(RecordsModel record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AMOUNT, record.getAmount());
        contentValues.put(COLUMN_DATE, String.valueOf(record.getDate()));
        contentValues.put(COLUMN_CATEGORY_ID, record.getCategoryId());
        contentValues.put(COLUMN_RECORD_TYPE, String.valueOf(record.getRecordType()));
        long insert = db.insert(RECORDS_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<RecordsModel> getRecords() {
        List<RecordsModel> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + RECORDS_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int recordsId = cursor.getInt(0);
                int amount = cursor.getInt(1);
                String date = cursor.getString(2);
                int categoryId = cursor.getInt(3);
                String recordType = cursor.getString(4);
                RecordTypeKey recordEnum = RecordTypeKey.valueOf(recordType);
                RecordsModel newRecord = new RecordsModel(recordsId, amount, date, categoryId, recordEnum);
                records.add(newRecord);
            } while (cursor.moveToNext());
        } else return null;
        cursor.close();
        db.close();
        return records;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
