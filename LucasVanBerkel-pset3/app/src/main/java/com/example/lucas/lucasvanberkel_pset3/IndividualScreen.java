package com.example.lucas.lucasvanberkel_pset3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IndividualScreen extends AppCompatActivity {

    MovieExtend movie;
    Bitmap bmp;
    boolean favorite;
    JSONArray movieArray;
    JSONObject movieJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_screen);

        Intent intent = getIntent();
        String movieID = intent.getStringExtra(searchList.ID);

        String searchURL = buildURL(movieID);

        load_data_from_api(searchURL);

        while (true) {
            if (movie != null && bmp != null) {
                setVariables();
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        updateButtons();
    }

    private void load_data_from_api(final String query){
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            protected Void doInBackground(String... strings) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(query).build();

                try {
                    Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    movieJSON = new JSONObject(result);

                    if (movieJSON.getString("Response").equals("True")) {
                        movie = new MovieExtend(movieJSON.getString("imdbID"),
                                movieJSON.getString("Title"),
                                movieJSON.getString("Poster"),
                                movieJSON.getString("Year"),
                                movieJSON.getString("Director"),
                                movieJSON.getString("Actors"),
                                movieJSON.getString("Plot"));
                    }
                } catch (IOException |JSONException e){
                    e.printStackTrace();
                }
                String link = movie.getLink();
                if (!link.equals("N/A")){
                    URL url = null;
                    try{
                        url = new URL(link);
                    } catch (MalformedURLException e){
                        e.printStackTrace();
                    }
                    bmp = null;
                    try{
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (IOException|NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.no_poster);

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        task.execute(query);
    }


    private void setVariables(){
        ImageView image = (ImageView) findViewById(R.id.moviePoster);
        image.setImageBitmap(bmp);
        ((TextView) findViewById(R.id.movieTitle)).setText(movie.getDescription());
        ((TextView) findViewById(R.id.movieYear)).setText(movie.getYear());
        ((TextView) findViewById(R.id.movieReg)).setText(movie.getReg());
        ((TextView) findViewById(R.id.movieActors)).setText(movie.getActors());
        ((TextView) findViewById(R.id.movieSummary)).setText(movie.getSummary());
        ((TextView) findViewById(R.id.movieTitle)).setText(movie.getDescription());

    }

    private String buildURL(String query){
        String homeURL = "http://www.omdbapi.com/?";
        String extra = "&plot=full&r=json";
        String movieTitle = "";
        try {
            movieTitle = "i=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return homeURL + movieTitle + extra;
    }

    private void updateButtons(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String movieArrayString = pref.getString("movieArray", null);
        try {
            favorite = false;
            movieArray = new JSONArray(movieArrayString);
            for (int i = 0; i < movieArray.length(); i++){
                String id = movieArray.getJSONObject(i).getString("imdbID");
                if (id.equals(movie.getId())){
                    favorite = true;
                    (findViewById(R.id.removeFromWatchlist)).setVisibility(View.VISIBLE);
                    break;
                }
            }
            if (!favorite){
                (findViewById(R.id.addToWatchlist)).setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void changeFavorite(View view){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        try {
            if (favorite){
                for (int i = 0; i < movieArray.length(); i++) {
                    JSONObject json = movieArray.getJSONObject(i);
                    if (json.getString("imdbID").equals(movieJSON.getString("imdbID"))) {
                        JSONArray movieArrayDupl = new JSONArray();
                        for (int j = 0; j < movieArray.length(); j++) {
                            if (i != j) {
                                movieArrayDupl.put(movieArray.getJSONObject(j));
                            }
                        }
                        movieArray = movieArrayDupl;
                        favorite = false;
                        break;
                    }
                }
                (findViewById(R.id.addToWatchlist)).setVisibility(View.VISIBLE);
                (findViewById(R.id.removeFromWatchlist)).setVisibility(View.INVISIBLE);
            } else {
                movieArray.put(movieJSON);
                favorite = true;
                (findViewById(R.id.removeFromWatchlist)).setVisibility(View.VISIBLE);
                (findViewById(R.id.addToWatchlist)).setVisibility(View.INVISIBLE);
            }

            String movieArrayString = movieArray.toString();
            editor.putString("movieArray", movieArrayString);
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
