package com.example.movierama;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Κατι λαθος εχω εδω.
        // Θελω να δημιουργησω ενα αρχειο .txt για να διαχειρηζομαι τις αγαπημένες ταινίες
        // TODO: 13/9/2021 χειρισμός favorites.txt για τις αγαπημενες ταινιες 
        File file = new File("favorites.txt");
        String favData = "";
        try {
            InputStream inputStream = new FileInputStream(file);
            favData = String.valueOf(inputStream.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.id.setText(mData.get(position).getId());
        holder.name.setText(mData.get(position).getName());
        holder.release.setText(mData.get(position).getReleaseD());
        // χειρησμος toggle button. κατι λαθος υπαρχει.
        if (mData.get(position).getName().matches(favData))
            holder.favorite.setText("Favorite");
        else
            holder.favorite.setText("not favorite");

        // glide library
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImg())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, movieDetails.class);
                intent.putExtra("name", mData.get(position).getName());
                intent.putExtra("rating", mData.get(position).getId());
                intent.putExtra("poster", mData.get(position).getImg());
                intent.putExtra("overview", mData.get(position).getOverview());
                intent.putExtra("release", mData.get(position).getReleaseD());
                mContext.startActivity(intent);
            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read και write στο favorites.txt
                //δεν μου ανοιγει το openOutputStream() και το openInputStream(). Κατι λαθος υπαρχει
                // TODO: 13/9/2021 Εγγραφή τίτλου ταινίας στο favorites.txt 
                String movieTitle = mData.get(position).getName();
                if (mData.get(position).isFavorite() == false) {
                    try {
                        mData.get(position).setFavorite(true);
                        OutputStream fos = new FileOutputStream(file);
                        fos.write(movieTitle.getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        // TODO: 13/9/2021 Διαγραφή ταινίας απο το favorites.txt 
                        mData.get(position).setFavorite(false);
                        InputStream fis = new FileInputStream(file);
                        String temp = String.valueOf(fis.read());
                        temp.replace(movieTitle, "");
                        fis.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        ImageView img;
        TextView release;
        ToggleButton favorite;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_text);
            name = itemView.findViewById(R.id.name_text);
            img = itemView.findViewById(R.id.imageView);
            release = itemView.findViewById(R.id.release);
            favorite = itemView.findViewById(R.id.toggleButton);


        }
    }


}
