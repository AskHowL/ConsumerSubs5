package com.example.consumersubs5.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.consumersubs5.db.DatabaseContract;

import static android.os.Build.ID;
import static com.example.consumersubs5.db.DatabaseContract.getColumnInt;
import static com.example.consumersubs5.db.DatabaseContract.getColumnString;



public class Movie implements Parcelable {

    private int id;
    private String img;
    private String title;
    private String desc;
    private String is_movie;



    public String getIs_movie() {
        return is_movie;
    }

    public void setIs_movie(String is_movie) {
        this.is_movie = is_movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.img);
        parcel.writeString(this.title);
        parcel.writeString(this.desc);
        parcel.writeInt(this.id);
        parcel.writeString(this.is_movie);
    }

    protected Movie(Parcel in) {
        this.img = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.id = in.readInt();
        this.is_movie = in.readString();
    }

    public Movie() {
    }


    public Movie(int id, String title, String description, String img) {
        this.id = id;
        this.title = title;
        this.desc = description;
        this.img = img;
    }


    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, ID);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumn.TITLE);
        this.desc = getColumnString(cursor, DatabaseContract.MovieColumn.DESCRIPTION);
        this.img = getColumnString(cursor, DatabaseContract.MovieColumn.PIC);
        this.is_movie = getColumnString(cursor, DatabaseContract.MovieColumn.IS_MOVIE);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
