package com.example.movierama;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;

 public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<MovieModelClass> mData;

    public Adaptery(Context mContext, List<MovieModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.rating.setText(mData.get(position).getRating());
        holder.name.setText(mData.get(position).getName());
        holder.release.setText(mData.get(position).getReleaseD());

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImg())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, movieDetails.class);
                intent.putExtra("name", mData.get(position).getName());
                intent.putExtra("rating", mData.get(position).getRating());
                intent.putExtra("poster", mData.get(position).getImg());
                intent.putExtra("overview", mData.get(position).getOverview());
                intent.putExtra("release", mData.get(position).getReleaseD());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rating;
        TextView name;
        ImageView img;
        TextView release;

        RatingBar ratingStars;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            int numStars = 0;

            rating = itemView.findViewById(R.id.ratingText);
            name = itemView.findViewById(R.id.title_text);
            img = itemView.findViewById(R.id.imageView);
            release = itemView.findViewById(R.id.release);
            ratingStars = itemView.findViewById(R.id.ratingStars);

        }
    }


}
