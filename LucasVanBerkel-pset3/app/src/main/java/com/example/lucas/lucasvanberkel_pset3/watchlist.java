package com.example.lucas.lucasvanberkel_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class watchlist extends AppCompatActivity {

    private ArrayList<Movie> movielist;
    private JSONArray movieArray;
    public final static String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String movieArrayString = pref.getString("movieArray", null);
        try {
            movieArray = new JSONArray(movieArrayString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        movielist = new ArrayList<>();

        populateMovieList();

        MyAdapter adapter = new MyAdapter(this, movielist);

        ListView listView = (ListView) findViewById(R.id.searchListview);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie;
                movie = (Movie) adapterView.getItemAtPosition(i);
                Intent viewItemIntent = new Intent(watchlist.this, IndividualScreen.class);
                viewItemIntent.putExtra(ID, movie.getId());
                startActivity(viewItemIntent);
            }
        });
    }

    private void populateMovieList(){
        try {
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject object = movieArray.getJSONObject(i);

                Movie movie = new Movie(object.getString("imdbID"), object.getString("Title"), object.getString("Poster"), object.getString("Year"));

                movielist.add(movie);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
