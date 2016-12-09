package com.example.lucas.lucasvanberkel_pset6;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiExecute extends AsyncTask<String, Void, String> {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        String query = strings[0];
        String result = "0";
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder().url(query).build();

        try {
            Response response = client.newCall(request).execute();

            result = response.body().string();

            return result;

        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
