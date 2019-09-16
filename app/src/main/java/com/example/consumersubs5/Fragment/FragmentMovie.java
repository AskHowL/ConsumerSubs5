package com.example.consumersubs5.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumersubs5.Adapter.RecyclerViewAdapterMovie;
import com.example.consumersubs5.Model.Movie;
import com.example.consumersubs5.MovieInterface;
import com.example.consumersubs5.R;

import java.util.ArrayList;

import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.CONTENT_URI;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.DESCRIPTION;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.ID;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.PIC;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.TITLE;

public class FragmentMovie extends Fragment implements MovieInterface {
    View v;

    private RecyclerView rvMovie;
    private RecyclerViewAdapterMovie adapter;
    private ProgressBar progressBar;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    //    private MovieHelper movieHelper;
    private boolean shouldRefreshOnResume = false;
    private ArrayList<Movie> list ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_fav_movie,container,false);
        rvMovie = v.findViewById(R.id.recyclerfavmovie_id);
        progressBar = v.findViewById(R.id.progressBarFavMovie);

        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);


        adapter = new RecyclerViewAdapterMovie(getActivity());

        if (savedInstanceState == null) {
            adapter.setListMovie(getMovie());
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovie(list);
            }
        }

        rvMovie.setAdapter(adapter);
        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovie() );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListMovie(movies);
    }

    private ArrayList<Movie> getMovie () {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        //movieHelper.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setListMovie(getMovie());
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }

}
