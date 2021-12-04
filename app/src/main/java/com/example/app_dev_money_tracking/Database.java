package com.example.app_dev_money_tracking;

import static com.example.app_dev_money_tracking.RecordTypeModel.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final String RECORDS_TABLE = "RECORDS_TABLE";
    public static final String CATEGORIES_TABLE = "CATEGORIES_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";
    public static final String COLUMN_RECORD_TYPE = "RECORD_TYPE";
    public static final String LOGIN_TABLE = "LOGIN_TABLE";
    public static final String COLUMN_LOGIN_EMAIL = "LOGIN_EMAIL";
    public static final String COLUMN_LOGIN_PASSWORD = "LOGIN_PASSWORD";
    public static final String COLUMN_BALANCE = "BALANCE";
    public static final String COLUMN_ADMIN = "ADMIN";
    public static final String COLUMN_THIRD_PARTY_LOGIN = "THIRD_PARTY_LOGIN";
    public static final String COLUMN_CURRENCY = "CURRENCY";
    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_NAME = "NAME";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE IF NOT EXISTS " + LOGIN_TABLE +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_LOGIN_EMAIL + " TEXT, " +
            COLUMN_LOGIN_PASSWORD + " TEXT, " +
            COLUMN_ADMIN + " INTEGER DEFAULT 0, " +
            COLUMN_BALANCE + " INTEGER, " +
            COLUMN_THIRD_PARTY_LOGIN + " INTEGER, " +
            COLUMN_CURRENCY + " TEXT )";

    private static final String CREATE_TABLE_RECORDS = "CREATE TABLE IF NOT EXISTS " + RECORDS_TABLE +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_AMOUNT + " INTEGER, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_CATEGORY_ID + " INTEGER, " +
            COLUMN_RECORD_TYPE + " INTEGER, " +
            COLUMN_CURRENCY + " TEXT )";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS " + CATEGORIES_TABLE +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_IMAGE + " INTEGER )";

    public Database(@Nullable Context context) {
        super(context, "moneyApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(CREATE_TABLE_RECORDS);
        db.execSQL(CREATE_TABLE_CATEGORIES);
    }

    public boolean addRecord(RecordsModel record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AMOUNT, record.getAmount());
        contentValues.put(COLUMN_DATE, String.valueOf(record.getDate()));
        contentValues.put(COLUMN_CATEGORY_ID, record.getCategoryId());
        contentValues.put(COLUMN_RECORD_TYPE, String.valueOf(record.getRecordType()));
        contentValues.put(COLUMN_CURRENCY, String.valueOf(record.getCurrency()));
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
                String currency = cursor.getString(5);
                RecordsModel newRecord = new RecordsModel(recordsId, amount, date, categoryId, recordEnum, currency);
                records.add(newRecord);
            } while (cursor.moveToNext());
        } else return null;
        cursor.close();
        db.close();
        return records;
    }

    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LOGIN_EMAIL, userModel.getEmail());
        contentValues.put(COLUMN_LOGIN_PASSWORD, userModel.getPassword());
        contentValues.put(COLUMN_BALANCE, 0);
        contentValues.put(COLUMN_ADMIN, 0);
        contentValues.put(COLUMN_THIRD_PARTY_LOGIN, userModel.getFbid());
        contentValues.put(COLUMN_CURRENCY, userModel.getCurrency());
        long insert = db.insert(LOGIN_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LOGIN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_LOGIN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_BALANCE, user.getBalance());
        contentValues.put(COLUMN_ADMIN, user.getAdmin());
        contentValues.put(COLUMN_ADMIN, user.getFbid());
        contentValues.put(COLUMN_CURRENCY, user.getCurrency());
        int affectedRows = db.update(LOGIN_TABLE, contentValues, "ID = " + user.getId(), null);
        return affectedRows == 0 ? false : true;
    }

    public UserModel getUserByEmail(String emailInput) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + LOGIN_TABLE + " WHERE " + COLUMN_LOGIN_EMAIL + " = '" + emailInput + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN_PASSWORD));
                int balance = cursor.getInt(cursor.getColumnIndex(COLUMN_BALANCE));
                int admin = cursor.getInt(cursor.getColumnIndex(COLUMN_ADMIN));
                String fbId = cursor.getString(cursor.getColumnIndex(COLUMN_THIRD_PARTY_LOGIN));
                String currency = cursor.getString(cursor.getColumnIndex(COLUMN_CURRENCY));
                UserModel user = new UserModel(id, email, password, balance, admin,fbId, currency);
                return user;
            } else return null;
        } else return null;
    }

    public boolean addCategory(Categories category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, category.getCategoryName());
        contentValues.put(COLUMN_IMAGE, category.getCategoryImg());

        long insert = db.insert(CATEGORIES_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Categories> getCategories() {
        List<Categories> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + CATEGORIES_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int image = cursor.getInt(2);
                Categories newCategory = new Categories(id, name, image);
                categories.add(newCategory);
            } while (cursor.moveToNext());
        } else return new ArrayList<>();
        cursor.close();
        db.close();
        return categories;
    }

    public Categories getCategoryById(int categoryId) {
        Categories category;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + CATEGORIES_TABLE + " WHERE " + COLUMN_ID + " = '" + categoryId + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int image = cursor.getInt(2);
                category = new Categories(id, name, image);
                return category;
            } else return null;
        } else return null;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECORDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        onCreate(db);
    }
}
