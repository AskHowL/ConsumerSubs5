package com.example.consumersubs5.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consumersubs5.Model.Movie;
import com.example.consumersubs5.Model.TV;
import com.example.consumersubs5.R;
import com.example.consumersubs5.db.MovieHelper;
import com.squareup.picasso.Picasso;

import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.CONTENT_URI;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.DESCRIPTION;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.ID;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.IS_MOVIE;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.PIC;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.TITLE;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String Extra_Movie = "extra_movie";
    public static final String Extra_TV = "extra_tv";
    private final AppCompatActivity activity = DetailActivity.this;
    private static HandlerThread handlerThread;
    private DataObserver myObserver;
    TextView tvTitle;
    TextView tvDesc;
    ImageView imgPhoto;
    Button btnDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);


        Movie selectedMovie = getIntent().getParcelableExtra(Extra_Movie);
        TV selectedTV = getIntent().getParcelableExtra(Extra_TV);

        if(selectedMovie!=null){

            tvTitle = findViewById(R.id.tv_detail_title);
            tvDesc = findViewById(R.id.tv_detail_desc);
            imgPhoto = findViewById(R.id.iv_detail_photo);


            tvTitle.setText(selectedMovie.getTitle());
            tvDesc.setText(selectedMovie.getDesc());
            Picasso.get().load(selectedMovie.getImg()).into(imgPhoto);

            setActionBarTitle("Movie Detail");
        }
        else if (selectedTV!=null){
            tvTitle = findViewById(R.id.tv_detail_title);
            tvDesc = findViewById(R.id.tv_detail_desc);
            imgPhoto = findViewById(R.id.iv_detail_photo);


            tvTitle.setText(selectedTV.getTitle());
            tvDesc.setText(selectedTV.getDesc());
            Picasso.get().load(selectedTV.getImg()).into(imgPhoto);

            setActionBarTitle("TV Detail");
        }

        btnDownload = findViewById(R.id.bt_download);
        btnDownload.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Movie selectedMovie = getIntent().getParcelableExtra(Extra_Movie);
        TV selectedTV = getIntent().getParcelableExtra(Extra_TV);
        if(selectedMovie!=null){
            saveFavorite();
        }
        else if (selectedTV!=null) {
            saveTVFavorite();
        }
    }


    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }


    public void saveFavorite(){

        Movie selectedMovie = getIntent().getParcelableExtra(Extra_Movie);
        ContentValues values = new ContentValues();
        values.put(ID , selectedMovie.getId());
        values.put(TITLE, selectedMovie.getTitle());
        values.put(DESCRIPTION, selectedMovie.getDesc());
        values.put(PIC, selectedMovie.getImg());
        values.put(IS_MOVIE , 1);
        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + selectedMovie.getId()), null, null, null, null);

        if (cursor.getCount() == 0) {

            getContentResolver().insert(CONTENT_URI, values);

            Toast.makeText(DetailActivity.this, getString(R.string.SaveFavMovie), Toast.LENGTH_SHORT).show();
        }else{
            getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + selectedMovie.getId()), ID , new String[]{String.valueOf(selectedMovie.getId())});
            Toast.makeText(DetailActivity.this, getString(R.string.DeleteFavMovie), Toast.LENGTH_SHORT).show();
        }

    }

    public void saveTVFavorite(){

        TV selectedMovie = getIntent().getParcelableExtra(Extra_TV);
        ContentValues values = new ContentValues();
        values.put(ID , selectedMovie.getId());
        values.put(TITLE, selectedMovie.getTitle());
        values.put(DESCRIPTION, selectedMovie.getDesc());
        values.put(PIC, selectedMovie.getImg());
        values.put(IS_MOVIE , 0);
        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + selectedMovie.getId()), null, null, null, null);

        if (cursor.getCount() == 0) {
            getContentResolver().insert(CONTENT_URI, values);
            Toast.makeText(DetailActivity.this, getString(R.string.SaveFavTV), Toast.LENGTH_SHORT).show();
        }else{
            getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + selectedMovie.getId()), ID , new String[]{String.valueOf(selectedMovie.getId())});
            Toast.makeText(DetailActivity.this, getString(R.string.DeleteFavTV), Toast.LENGTH_SHORT).show();
        }
    }


    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //new LoadNoteAsync(context, (LoadNotesCallback) context).execute();
        }
    }

}
