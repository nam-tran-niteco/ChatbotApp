package com.example.namtran.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Tran on 27-May-17.
 */

public class DbUtil {

    private DBHelper dbHelper;

    public DbUtil(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertUserInfo (String key, String value) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.UserEntry.COLUMN_KEY, key);
        values.put(DBHelper.UserEntry.COLUMN_VALUE, value);

        return sqLiteDatabase.insert(DBHelper.UserEntry.TABLE_NAME, null, values);
    }

    public long insertChat (String userMessage, String botMessage) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.ChatEntry.COLUMN_USER_MESSAGE, userMessage);
        values.put(DBHelper.ChatEntry.COLUMN_BOT_MESSAGE, botMessage);

       return sqLiteDatabase.insert(DBHelper.ChatEntry.TABLE_NAME, null, values);
    }

    public Cursor getUserInfo (String key) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.UserEntry._ID,
                DBHelper.UserEntry.COLUMN_KEY,
                DBHelper.UserEntry.COLUMN_VALUE
        };

        String selection = DBHelper.UserEntry.COLUMN_KEY + " = ?";
        String[] selectionArgs = { key };

        String sortOrder =
                DBHelper.UserEntry.COLUMN_KEY + " DESC";

        return sqLiteDatabase.query(
                DBHelper.UserEntry.TABLE_NAME,            // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

    public Cursor getChatDialog () {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.ChatEntry._ID,
                DBHelper.ChatEntry.COLUMN_USER_MESSAGE,
                DBHelper.ChatEntry.COLUMN_BOT_MESSAGE
        };

        String sortOrder =
                DBHelper.ChatEntry._ID + " DESC";

        return sqLiteDatabase.query(
                DBHelper.ChatEntry.TABLE_NAME,            // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

}
