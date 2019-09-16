package com.example.consumersubs5.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumersubs5.Activity.DetailActivity;
import com.example.consumersubs5.Model.TV;
import com.example.consumersubs5.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterTV extends RecyclerView.Adapter<RecyclerViewAdapterTV.MyViewHolder> {
    private final ArrayList<TV> listTV = new ArrayList<>();

    private final Activity activity;

    public RecyclerViewAdapterTV(Activity activity) {
        this.activity = activity;
    }


    public ArrayList<TV> getListTV() {
        return listTV;
    }


    public void setListTV(ArrayList<TV> listTV) {
        this.listTV.clear();
        this.listTV.addAll(listTV);
        notifyDataSetChanged();
    }



    public void updateItem(int position, TV tv) {
        this.listTV.set(position, tv);
        notifyItemChanged(position, tv);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int i) {

        holder.bind(listTV.get(i));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(v.getContext(), DetailActivity.class);
                //mengirimkan data yang dipilih dengan identitas Extra_Movie
                x.putExtra(DetailActivity.Extra_TV, listTV.get(i));
                v.getContext().startActivity(x);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listTV.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder   {
        private TextView tv_title, tv_desc;
        private ImageView iv_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title_movie);
            tv_desc = itemView.findViewById(R.id.tv_desc_movie);
            iv_img = itemView.findViewById(R.id.img_movie);

        }

        void bind(TV tv) {
            tv_title.setText(tv.getTitle());
            tv_desc.setText(tv.getDesc());
            Picasso.get().load(tv.getImg()).into(iv_img);

        }
    }


}
