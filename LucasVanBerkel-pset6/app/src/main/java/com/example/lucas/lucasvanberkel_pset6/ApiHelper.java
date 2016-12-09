package com.example.lucas.lucasvanberkel_pset6;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ApiHelper {

    private static final String PATH = "https://api.themoviedb.org/3/search/";
    private static final String KEY = "&api_key=e91241515c32f872b2352f33e928eb99";

    private List<Item> itemList;

    public List<Item> load_data_from_api(final String query, int j){
        itemList = new ArrayList<>();
        JSONObject total;
        String keyTitle = "0";
        String keyImg = "0";
        String keyExtra = "0";
        switch(j){
            case 0:
                keyTitle = "title";
                keyImg = "poster_path";
                keyExtra = "release_date";
                break;
            case 1:
                keyTitle = "name";
                keyImg = "poster_path";
                keyExtra = "first_air_date";
                break;
            case 2:
                keyTitle = "name";
                keyImg = "profile_path";
                keyExtra = "known_for";
                break;

        }
        try {
            String jsonString = new ApiExecute().execute(query).get();


            total = new JSONObject(jsonString);

            if (total.has("total_results")) {
                JSONArray array = new JSONArray(total.getString("results"));

                int limit = 20;
                if (Integer.parseInt(total.getString("total_results")) < 20){
                    limit = Integer.parseInt(total.getString("total_results"));
                }

                for (int i = 0; i < limit; i++) {
                    JSONObject object = array.getJSONObject(i);

                    String extra = object.getString(keyExtra);
                    if(j == 2){
                        extra = concatenateMovies(extra);
                    }

                    Item item = new Item(object.getString(keyTitle), object.getString(keyImg), extra, false);

                    itemList.add(item);
                }
            }
            Log.d("TAG", "value = " + itemList.size());
        } catch (JSONException | InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        return itemList;
    }

    private String concatenateMovies(String extra) {
        String finalExtra = "";
        try {
            JSONArray temp = new JSONArray(extra);

            int limit = 5;
            if (temp.length() < 5) {
                limit = temp.length();
            }

            int i;

            for (i = 0; i < limit-1; i++) {
                JSONObject object = temp.getJSONObject(i);

                if(object.has("name")) {
                    finalExtra = finalExtra + object.getString("name") + ", ";
                } else {
                    finalExtra = finalExtra + object.getString("title") + ", ";
                }
            }

            JSONObject object = temp.getJSONObject(i);

            if(object.has("name")) {
                finalExtra = finalExtra + object.getString("name");
            } else {
                finalExtra = finalExtra + object.getString("title");
            }


        } catch (JSONException e){
            e.printStackTrace();
        }

        return finalExtra;

    }

    public String buildURL(String query, int i){
        String[] sortOfSearch = {"movie", "tv", "person"};
        String homeURL = PATH;
        String extra = sortOfSearch[i] + "?";
        String queryUrl = "";
        try {
            queryUrl = "query=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return homeURL + extra + queryUrl + KEY;
    }
}
