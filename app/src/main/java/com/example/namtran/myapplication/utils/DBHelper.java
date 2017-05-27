package com.example.namtran.myapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Tran on 27-May-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Chatbot.db";

    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_KEY + " TEXT," +
                    UserEntry.COLUMN_VALUE + " TEXT)";

    private static final String SQL_CREATE_CHAT_TABLE =
            "CREATE TABLE " + ChatEntry.TABLE_NAME + " (" +
                    ChatEntry._ID + " INTEGER PRIMARY KEY," +
                    ChatEntry.COLUMN_USER_MESSAGE + " TEXT," +
                    ChatEntry.COLUMN_BOT_MESSAGE + " TEXT)";

    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    private static final String SQL_DELETE_CHAT_TABLE =
            "DROP TABLE IF EXISTS " + ChatEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER_TABLE);
        db.execSQL(SQL_DELETE_CHAT_TABLE);
        onCreate(db);
    }

    public class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "userinfo";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_VALUE = "value";
    }

    public class ChatEntry implements BaseColumns {
        public static final String TABLE_NAME = "chatdialog";
        public static final String COLUMN_USER_MESSAGE = "user";
        public static final String COLUMN_BOT_MESSAGE = "bot";
    }

}
