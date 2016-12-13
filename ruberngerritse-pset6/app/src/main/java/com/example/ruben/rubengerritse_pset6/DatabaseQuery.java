package com.example.ruben.rubengerritse_pset6;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This file describes the class DatabaseQuery, which is an extension of the AsyncTask class.
 * DatabaseQuery reads the data given an url and returns as a String.
 */

public class DatabaseQuery extends AsyncTask<URL,Integer,String> {

//    Reads an url in the background and retuns the data in a String.
    @Override
    protected String doInBackground(URL... params) {
        URL url = params[0];
        String string = "";

        try {
            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((inputLine = in.readLine()) != null) {
                string += inputLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
}
