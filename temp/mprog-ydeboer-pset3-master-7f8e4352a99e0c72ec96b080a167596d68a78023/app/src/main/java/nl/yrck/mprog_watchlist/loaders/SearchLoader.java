package nl.yrck.mprog_watchlist.loaders;


import android.content.Context;

import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOError;

import nl.yrck.mprog_watchlist.api.Movie;
import nl.yrck.mprog_watchlist.api.MovieSearchResult;
import nl.yrck.mprog_watchlist.api.OMDbAPI;

public class SearchLoader extends AsyncTaskLoader<MovieSearchResult> {

    String searchQuery;

    public SearchLoader(Context context, String searchQuery) {
        super(context);
        this.searchQuery = searchQuery;
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
    public MovieSearchResult loadInBackground() {
        OMDbAPI omDbAPI = new OMDbAPI();
        return omDbAPI.Search(searchQuery, null, null);
    }

    @Override
    public void deliverResult(MovieSearchResult movieSearchResult) {
        super.deliverResult(movieSearchResult);
    }
}
