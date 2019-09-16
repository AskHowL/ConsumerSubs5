package com.example.consumersubs5.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.consumersubs5.Activity.DetailActivity;
import com.example.consumersubs5.db.MovieHelper;

import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.CONTENT_URI;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.TABLE_MOVIE;

public class ConsumerMovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_TITLE = 2;
    private static final int TV = 3;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;
    public static final String AUTHORITY = "com.example.subs5";
    private static final String DATABASE_TABLE = TABLE_MOVIE;

    static {
        // content://com.example.subs5/favmovie
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        // content://com.example.subs5/favmovie/id
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_TITLE);

        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "TV", TV);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        movieHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryMovieProvider();
                break;
            case MOVIE_TITLE:
                cursor = movieHelper.queryByTitleProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = movieHelper.queryTVProvider();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        movieHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new DetailActivity.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        movieHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_TITLE:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new DetailActivity.DataObserver(new Handler(), getContext()));
        return deleted;
    }
}