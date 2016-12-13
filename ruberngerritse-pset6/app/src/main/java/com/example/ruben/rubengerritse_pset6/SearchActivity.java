package com.example.ruben.rubengerritse_pset6;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

/**
 * This file describes the class SearchActivity, which is an extension of the BaseActivity. It
 * allows the user to search games using the giantbomb api
 */

public class SearchActivity extends BaseActivity {

//    Url parameters
    private static final String PATH = "http://www.giantbomb.com/api/search";
    private static final String KEY = "5496ad3bff8caf2cbadfe3dbbd0bc63c2d41ff34";
    private static final String FORMAT = "json";
    private static final String FIELD_LIST = "id,name,image,original_release_date";
    private static final String RESOURCES = "game";

    private RecyclerView recyclerView;
    private String urlStringBase;

//    Set the layout of this activity and the recyclerview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = (RecyclerView) findViewById(R.id.search_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        urlStringBase = PATH;
        urlStringBase += String.format("?api_key=%s", KEY);
        urlStringBase += String.format("&format=%s", FORMAT);
        urlStringBase += String.format("&field_list=%s", FIELD_LIST);
        urlStringBase += String.format("&resources=%s", RESOURCES);
    }

//    Searches the api using the query inputted by the user and updates the recyclerview given the
//    output of the api
    public void searchQuery(View view) {
        EditText editText = (EditText) findViewById(R.id.search_et);
        String query = editText.getText().toString().trim();

        try {
            String urlString = urlStringBase + String.format("&query=%s",
                    URLEncoder.encode(query, "UTF-8"));
            URL url = new URL(urlString);

            String jsonString = new DatabaseQuery().execute(url).get();
            JSONObject json = new JSONObject(jsonString);
            if (json.getString("status_code").equals("1")) {
                String resultsString = json.getString("results");
                JSONArray resultsArray = new JSONArray(resultsString);
                RecyclerView.Adapter adapter = new SearchAdapter(resultsArray);
                recyclerView.setAdapter(adapter);
            }
        } catch (UnsupportedEncodingException | MalformedURLException | InterruptedException |
                ExecutionException | JSONException e) {
            e.printStackTrace();
        }

    }
}
