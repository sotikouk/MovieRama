package com.example.movierama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // json link to the internet
    private static final String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=30842f7c80f80bb3ad8a2fb98195544d&language=en-US&page=1";
    private static String JSON_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key=30842f7c80f80bb3ad8a2fb98195544d&language=en-US&page=1&include_adult=false&query=";
    private static final String JSON_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=30842f7c80f80bb3ad8a2fb98195544d&language=en-US&page=1";

    List<MovieModelClass> movieList;
    RecyclerView recyclerview;

    EditText movieName;
    Button search, popular, topRated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        recyclerview = findViewById(R.id.recyclerview);

        GetData getData = new GetData();
        getData.execute(JSON_URL);

        search = findViewById(R.id.buttonSearch);
        popular = findViewById(R.id.popularButton);
        topRated = findViewById(R.id.topRatedButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList.clear();
                movieName = findViewById(R.id.searchMovie);
                String movieN = movieName.getText().toString();
                if(isConnected() && movieN != null){
                    movieN.replaceAll(" ","+");
                    String url = JSON_SEARCH+'"'+movieN+'"';
                    new GetData().execute(url);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList.clear();
                if(isConnected()){
                    new GetData().execute(JSON_URL);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        topRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList.clear();
                if(isConnected()){
                    new GetData().execute(JSON_TOP_RATED);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != connectivityManager.TYPE_WIFI && networkInfo.getType() != connectivityManager.TYPE_MOBILE)){
            return false;
        }
        return true;
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try {
                HttpURLConnection urlConnection = null;

                try {
                    URL url;
                    url = new URL(strings[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {

                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0 ; i< jsonArray.length() ; i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("vote_average"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    model.setReleaseD(jsonObject1.getString("release_date"));
                    model.setOverview(jsonObject1.getString("overview"));

                    movieList.add(model);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);


        }
    }

            private void PutDataIntoRecyclerView (List<MovieModelClass> movieList) {

                Adaptery adaptery = new Adaptery(this, movieList);
                recyclerview.setLayoutManager(new LinearLayoutManager(this));

                recyclerview.setAdapter(adaptery);

            }

}