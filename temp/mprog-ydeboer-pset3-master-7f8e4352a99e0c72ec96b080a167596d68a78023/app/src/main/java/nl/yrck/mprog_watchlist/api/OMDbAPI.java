package nl.yrck.mprog_watchlist.api;

import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class OMDbAPI {

    private static String BASE_URL = "http://www.omdbapi.com/";

    public OMDbAPI() {

    }

    public MovieSearchResult Search(String query, String type, String year) {
        try {
            URL url = buildSearchUrl(query, type, year);
            Log.d("Opening url", url.toString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(url, MovieSearchResult.class);
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public Movie getMovie(String imdbId) {
        try {
            URL url = new URL(BASE_URL + "?i=" + imdbId);
            Log.d("Opening url", url.toString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(url, Movie.class);
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public MovieFull getMovieFull(String imdbId) {
        try {
            URL url = new URL(BASE_URL + "?i=" + imdbId + "&plot=full");
            Log.d("Opening url", url.toString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(url, MovieFull.class);
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    private URL buildSearchUrl(String query, String type, String year)
            throws MalformedURLException {
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("s", query)
                .build();

        if (type != null) {
            uri = uri.buildUpon()
                    .appendQueryParameter("type", type)
                    .build();
        }

        if (year != null) {
            uri = uri.buildUpon()
                    .appendQueryParameter("y", year)
                    .build();
        }

        return new URL(uri.toString());
    }
}
