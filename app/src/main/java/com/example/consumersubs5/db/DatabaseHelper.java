package com.example.consumersubs5.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.TABLE_MOVIE;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbMovieApp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE= String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            DatabaseContract.MovieColumn._ID,
            DatabaseContract.MovieColumn.ID,
            DatabaseContract.MovieColumn.TITLE,
            DatabaseContract.MovieColumn.DESCRIPTION,
            DatabaseContract.MovieColumn.PIC,
            DatabaseContract.MovieColumn.IS_MOVIE
    );


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }


}
