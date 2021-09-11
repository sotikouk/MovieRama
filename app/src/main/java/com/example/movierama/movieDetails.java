package com.example.movierama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class movieDetails extends AppCompatActivity {

    ImageView poster;
    TextView title, rating, release, details;


    String name, rate, overview, img, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        poster = findViewById(R.id.poster);
        title = findViewById(R.id.movieTitle);
        rating = findViewById(R.id.movieRating);
        release = findViewById(R.id.movieRelease);
        details = findViewById(R.id.overview);

        getData();

        setData();
    }

    private void getData() {

        name = getIntent().getStringExtra("name");
        rate = getIntent().getStringExtra("rating");
        img = getIntent().getStringExtra("poster");
        overview = getIntent().getStringExtra("overview");
        date = getIntent().getStringExtra("release");
    }

    private void setData(){
        title.setText(name);
        rating.setText("Rating:"+rate);
        release.setText("Release Date:"+date);
        details.setText(overview);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+img)
                .into(poster);
    }
}