package com.example.lucas.lucasvanberkel_pset6.api;

import android.util.Log;

import com.example.lucas.lucasvanberkel_pset6.classes.Item;
import com.example.lucas.lucasvanberkel_pset6.classes.ItemExtended;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ApiHelper {

    private static final String PATH = "https://api.themoviedb.org/3/";
    private static final String KEY = "&api_key=e91241515c32f872b2352f33e928eb99";

    public List<Item> load_data_from_api(final String query, int j){
        List<Item> itemList = new ArrayList<>();
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
            String jsonString;
            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder().url(query).build();
            Response response = client.newCall(request).execute();

            jsonString = response.body().string();


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
                        extra = concatenateJSONArray(extra);
                    }

                    Item item = new Item(object.getString(keyTitle), object.getString(keyImg), extra, j, object.getInt("id"));

                    itemList.add(item);
                }
            }
            Log.d("TAG", "value = " + itemList.size());
        } catch (JSONException | IOException e){
            e.printStackTrace();
        }
        return itemList;
    }

    public ItemExtended load_data_from_api_indi(final String query, int j){
        ItemExtended item = null;
        JSONObject result;
        String keyTitle = "0";
        String keyImg = "0";
        String keyExtra = "0";
        String keyExtra2 = "0";
        String keyExtra3 = "0";
        switch(j){
            case 0:
                keyTitle = "title";
                keyImg = "poster_path";
                keyExtra = "release_date";
                keyExtra2 = "genres";
                keyExtra3 = "overview";
                break;
            case 1:
                keyTitle = "name";
                keyImg = "poster_path";
                keyExtra = "first_air_date";
                keyExtra2 = "genres";
                keyExtra3 = "overview";
                break;
            case 2:
                keyTitle = "name";
                keyImg = "profile_path";
                keyExtra = "birthday";
                keyExtra2 = "place_of_birth";
                keyExtra3 = "biography";
                break;

        }
        try {
            String jsonString;
            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder().url(query).build();
            Response response = client.newCall(request).execute();

            jsonString = response.body().string();


            result = new JSONObject(jsonString);
            String extra2 = result.getString(keyExtra2);
            if(j == 0|j==1){
                extra2 = concatenateJSONArray(extra2);
            }
            item = new ItemExtended(result.getString(keyTitle), result.getString(keyImg), result.getString(keyExtra), j, result.getInt("id"), extra2, result.getString(keyExtra3));
            Log.d("TAG", "Succes!");
        } catch (JSONException | IOException e){
            e.printStackTrace();
        }
        return item;
    }

    public String buildURL(String query, int i){
        String[] sortOfSearch = {"movie", "tv", "person"};
        String homeURL = PATH + "search/";
        String extra = sortOfSearch[i] + "?";
        String queryUrl = "";
        try {
            queryUrl = "query=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return homeURL + extra + queryUrl + KEY;
    }

    public String buildURLIndi(int query, int i){
        String[] sortOfSearch = {"movie", "tv", "person"};
        String homeURL = PATH;
        String extra = sortOfSearch[i] + "/" + Integer.toString(query) + "?";
        return homeURL + extra + KEY;
    }

    private String concatenateJSONArray(String extra) {
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
}
