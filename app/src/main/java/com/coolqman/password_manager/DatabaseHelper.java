package com.coolqman.password_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "passwords_db";

    // Table name and column names
    private String TABLE_PASSWORDS;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WEBSITE = "website";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TIMESTAMP = "timestamp";


    public DatabaseHelper(Context context, String userId) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.TABLE_PASSWORDS = "user_" + userId;
        createUserTable();
    }

    private void createUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_PASSWORDS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PASSWORDS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_WEBSITE + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_TIMESTAMP + " BIGINT" + ")";
        db.execSQL(CREATE_PASSWORDS_TABLE);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PASSWORDS_TABLE = "CREATE TABLE " + TABLE_PASSWORDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_WEBSITE + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_TIMESTAMP + " BIGINT" + ")";
        db.execSQL(CREATE_PASSWORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORDS);
        onCreate(db);
    }

    // Insert a new password
    public void addPassword(Password password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_WEBSITE, password.getWebsite());
        values.put(COLUMN_USERNAME, password.getUsername());
        values.put(COLUMN_PASSWORD, password.getPassword());
        values.put(COLUMN_ID, password.getId());
        values.put(COLUMN_TIMESTAMP, password.getTimestamp());

        db.insert(TABLE_PASSWORDS, null, values);
        db.close();
    }

    // Get all passwords
    public List<Password> getAllPasswords() {
        List<Password> passwordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PASSWORDS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String website = cursor.getString(cursor.getColumnIndex(COLUMN_WEBSITE));
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                long timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));

                Password p = new Password(id, website, username, password, timestamp);
                passwordList.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return passwordList;
    }

    // Update a password
    public void updatePassword(Password password, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEBSITE, password.getWebsite());
        values.put(COLUMN_USERNAME, password.getUsername());
        values.put(COLUMN_PASSWORD, password.getPassword());
        values.put(COLUMN_TIMESTAMP, password.getTimestamp());

        db.update(TABLE_PASSWORDS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(password.getId())});
        db.close();
    }

    // Delete a password
    public void deletePassword(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PASSWORDS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllPasswords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PASSWORDS);
        db.close();
    }
}
