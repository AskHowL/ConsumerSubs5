package com.example.consumersubs5.Fragment;

import android.database.Cursor;
import android.net.Uri;
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

import com.example.consumersubs5.Adapter.RecyclerViewAdapterTV;
import com.example.consumersubs5.Model.TV;
import com.example.consumersubs5.R;
import com.example.consumersubs5.TVInterface;

import java.util.ArrayList;

import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.CONTENT_URI;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.DESCRIPTION;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.ID;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.PIC;
import static com.example.consumersubs5.db.DatabaseContract.MovieColumn.TITLE;

public class FragmentTV extends Fragment implements TVInterface {

    View v;

    private RecyclerView rvMovie;
    private static RecyclerViewAdapterTV adapter;
    private ProgressBar progressBar;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    //private MovieHelper movieHelper;
    private boolean shouldRefreshOnResume = false;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_fav_tv,container,false);

        rvMovie = v.findViewById(R.id.recyclerfavtv_id);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);

        progressBar = v.findViewById(R.id.progressBarFavTV);
/*
        // Hide Search di fragment fav
        SearchView simpleSearchView = v.findViewById(R.id.search_id);
        simpleSearchView.setVisibility(View.GONE);*/

        adapter = new RecyclerViewAdapterTV(getActivity());
//        adapter.setListTV(movieHelper.getAllTVFavorite());

        rvMovie.setAdapter(adapter);


        if (savedInstanceState == null) {
            adapter.setListTV(getTV());
        } else {
            ArrayList<TV> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTV(list);
            }
        }

        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTV());
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
    public void postExecute(ArrayList<TV> tv) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListTV(tv);


    }


    private ArrayList<TV> getTV () {
        ArrayList<TV> arrayList = new ArrayList<>();
        Cursor cursor = getContext().getContentResolver().query(Uri.parse(CONTENT_URI+"TV"), null, null, null, null);
        cursor.moveToFirst();
        TV tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new TV();
                tv.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        //movieHelper.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check should we need to refresh the fragment
        adapter.setListTV(getTV());
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }


}
