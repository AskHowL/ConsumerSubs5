package com.example.consumersubs5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.consumersubs5.Model.Movie;
import com.example.consumersubs5.Model.TV;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.DESCRIPTION;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.ID;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.IS_MOVIE;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.PIC;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.TABLE_MOVIE;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.TITLE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }


    // INSERT
    public long addMovieFavorite(Movie movie){
        ContentValues args = new ContentValues();
        args.put(ID , movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(DESCRIPTION, movie.getDesc());
        args.put(PIC, movie.getImg());
        args.put(IS_MOVIE, "1");
        return database.insert(DATABASE_TABLE, null, args);
    }

    public long addTVFavorite(TV tv){
        ContentValues args = new ContentValues();
        args.put(ID , tv.getId());
        args.put(TITLE, tv.getTitle());
        args.put(DESCRIPTION, tv.getDesc());
        args.put(PIC, tv.getImg());
        args.put(IS_MOVIE, "0");
        return database.insert(DATABASE_TABLE, null, args);
    }

    // DELETE
    public int deleteFavorite(String title){
        return database.delete(TABLE_MOVIE, TITLE + " = '" + title + "'", null);
    }

    // SELECT
    public ArrayList<Movie> getAllMovieFavorite(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                "IS_MOVIE='1'",
                null ,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setImg(cursor.getString(cursor.getColumnIndexOrThrow(PIC)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public ArrayList<TV> getAllTVFavorite(){
        ArrayList<TV> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                "IS_MOVIE='0'",
                null ,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TV tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new TV();
                tv.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tv.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                tv.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tv.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tv.setImg(cursor.getString(cursor.getColumnIndexOrThrow(PIC)));

                arrayList.add(tv);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public static boolean CheckData(Movie movie) {
        String Query = "Select * from " + TABLE_MOVIE + " where " + TITLE + " = '" + movie.getTitle() + "'";
        Cursor cursor = database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public static boolean CheckDataTV(TV tv) {
        String Query = "Select * from " + TABLE_MOVIE + " where " + TITLE + " = '" + tv.getTitle() + "'";
        Cursor cursor = database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //ambil pic nya aja
    public ArrayList<Movie> getPicFav()
    {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null ,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setImg(cursor.getString(cursor.getColumnIndexOrThrow(PIC)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryByTitleProvider(String id) {
        return database.query(DATABASE_TABLE, null
                ,  ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryMovieProvider() {
        return database.query(DATABASE_TABLE
                , null
                , "IS_MOVIE='1'"
                , null
                , null
                , null
                , TITLE + " ASC");
    }

    public Cursor queryTVProvider() {
        return database.query(DATABASE_TABLE
                , null
                , "IS_MOVIE='0'"
                , null
                , null
                , null
                , TITLE + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }


    public int deleteProvider(String id ) {
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }
}
