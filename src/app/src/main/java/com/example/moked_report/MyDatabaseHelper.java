package com.example.moked_report;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String MYDATABASE = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    // Define table and column names
    // Table name and column names
    public static final String TABLE_USER_INFORMATION = "user_information";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_JOB = "job";

    // SQL statement to create the table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_USER_INFORMATION + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_JOB + " TEXT)";

    public MyDatabaseHelper(Context context) {
        super(context, MYDATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFORMATION);
        onCreate(db);
    }

    // CRUD functions

    // Create a new user
    public long addUser(String name, String job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_JOB, job);
        long newRowId = db.insert(TABLE_USER_INFORMATION, null, values);
        db.close();
        return newRowId;
    }

    // Read all users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER_INFORMATION, null, null, null, null, null, null);
    }

    // Update user information
    public int updateUser(long id, String name, String job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_JOB, job);
        return db.update(TABLE_USER_INFORMATION, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Delete a user
    public int deleteUser(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER_INFORMATION, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}