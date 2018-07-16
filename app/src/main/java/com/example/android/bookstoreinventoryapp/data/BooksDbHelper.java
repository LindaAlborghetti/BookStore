package com.example.android.bookstoreinventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BooksDbHelper extends SQLiteOpenHelper {

    // Name of the database file
    private static final String DATABASE_NAME = "bookstore.db";

    // Database version. If you change the schema you must increment the database version
    private static final int DATABASE_VERSION = 1;


    public BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //This is called when the database is created the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the books table
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BooksContract.BooksEntry.TABLE_NAME + " ("
                + BooksContract.BooksEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BooksContract.BooksEntry.COLUMN_BOOK_TITLE + " TEXT NOT NULL, "
                + BooksContract.BooksEntry.COLUMN_AUTHOR + " TEXT NOT NULL, "
                + BooksContract.BooksEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 1, "
                + BooksContract.BooksEntry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + BooksContract.BooksEntry.COLUMN_SUPPLIER + " TEXT NOT NULL, "
                + BooksContract.BooksEntry.COLUMN_PHONE_NUMBER + " INTEGER NOT NULL DEFAULT 0);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
