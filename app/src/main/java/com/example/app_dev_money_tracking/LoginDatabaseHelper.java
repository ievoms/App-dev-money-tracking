package com.example.app_dev_money_tracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;

public class LoginDatabaseHelper extends SQLiteOpenHelper {
    public static final String LOGIN_TABLE = "LOGIN_TABLE";
    public static final String COLUMN_LOGIN_EMAIL = "LOGIN_EMAIL";
    public static final String COLUMN_LOGIN_PASSWORD = "LOGIN_PASSWORD";
    public static final String COLUMN_ID = "ID";
    private ContentValues contentValues;

    public LoginDatabaseHelper(@Nullable Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + LOGIN_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LOGIN_EMAIL + " TEXT, " + COLUMN_LOGIN_PASSWORD + " TEXT )";
        db.execSQL(createTableStatement);

    }

    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LOGIN_EMAIL, userModel.getEmail());
        contentValues.put(COLUMN_LOGIN_PASSWORD, userModel.getPassword());
        long insert = db.insert(LOGIN_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public UserModel getUser(String emailInput) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + LOGIN_TABLE + " WHERE " + COLUMN_LOGIN_EMAIL + " = '" + emailInput + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN_PASSWORD));
                UserModel user = new UserModel(id, email, password);
                return user;

            } else return null;
        } else return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
