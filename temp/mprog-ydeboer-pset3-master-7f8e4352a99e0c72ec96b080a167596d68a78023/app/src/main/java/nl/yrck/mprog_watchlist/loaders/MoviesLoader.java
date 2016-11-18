package nl.yrck.mprog_watchlist.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.yrck.mprog_watchlist.api.Movie;
import nl.yrck.mprog_watchlist.api.OMDbAPI;


public class MoviesLoader extends AsyncTaskLoader<List<Movie>> {

    String[] imdbIds;

    public MoviesLoader(Context context, String[] imdbIds) {
        super(context);
        this.imdbIds = imdbIds;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        OMDbAPI omDbAPI = new OMDbAPI();

        List<Movie> movies = new ArrayList<>();
        for (String imdbId : imdbIds) {
            movies.add(omDbAPI.getMovie(imdbId));
        }
        return movies;
    }

    @Override
    public void deliverResult(List<Movie> movies) {
        Log.d("searchloader", "deliver result");
        super.deliverResult(movies);
    }
}
