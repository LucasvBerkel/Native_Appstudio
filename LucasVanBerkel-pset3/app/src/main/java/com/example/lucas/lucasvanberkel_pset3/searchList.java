package com.example.lucas.lucasvanberkel_pset3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class searchList extends AppCompatActivity {

    private ArrayList<Movie> movielist;
    public final static String ID = "id";
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent intent = getIntent();
        String searchquery = intent.getStringExtra(startScreen.SEARCH);

        movielist = new ArrayList<>();

        String searchURL = buildURL(searchquery);

        adapter = new MyAdapter(this, movielist);

        ListView listView = (ListView) findViewById(R.id.searchListview);
        listView.setAdapter(adapter);

        load_data_from_api(searchURL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie;
                movie = (Movie) adapterView.getItemAtPosition(i);
                Intent viewItemIntent = new Intent(searchList.this, IndividualScreen.class);
                viewItemIntent.putExtra(ID, movie.getId());
                startActivity(viewItemIntent);
            }
        });

        while (true) {
            if (movielist.size() != 0) {
                adapter.notifyDataSetChanged();
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void load_data_from_api(final String query){
            AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                protected Void doInBackground(String... strings) {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(query).build();

                    JSONObject total;
                    try {
                        Response response = client.newCall(request).execute();

                        String result = response.body().string();

                        total = new JSONObject(result);

                        if (total.getString("Response").equals("True")) {
                            Log.d("Tag1", "Geen toast");
                            JSONArray array = new JSONArray(total.getString("Search"));

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                Movie movie = new Movie(object.getString("imdbID"), object.getString("Title"), object.getString("Poster"), object.getString("Year"));

                                movielist.add(movie);
                            }
                        }
                        Log.d("TAG", "value = " + movielist.size());
                    } catch (IOException|JSONException e){
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                }
            };
        adapter.notifyDataSetChanged();
        task.execute(query);
    }

    private String buildURL(String query){
        String homeURL = "http://www.omdbapi.com/?";
        String extra = "&plot=short&r=json";
        String movieTitle = "";
        try {
            movieTitle = "s=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
            return homeURL + movieTitle + extra;
    }
}
