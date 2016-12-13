package com.example.ruben.rubengerritse_pset6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * This file contains the class ImageDownload, which is an extension of the AsyncTask. ImageDownload
 * reads the data from an url and returns it as a Bitmap.
 */

public class ImageDownload extends AsyncTask<URL,Integer,Bitmap> {

//    Read data of an url in the background and return it as a Bitmap
    @Override
    protected Bitmap doInBackground(URL... params) {
        URL url = params[0];

        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }
}
