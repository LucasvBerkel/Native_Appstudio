package nl.yrck.mprog_watchlist.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import nl.yrck.mprog_watchlist.api.MovieFull;
import nl.yrck.mprog_watchlist.api.OMDbAPI;

public class MovieFullLoader extends AsyncTaskLoader<MovieFull> {

    String imdbId;

    public MovieFullLoader(Context context, String imdbId) {
        super(context);
        this.imdbId = imdbId;
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
    public MovieFull loadInBackground() {
        OMDbAPI omDbAPI = new OMDbAPI();
        return omDbAPI.getMovieFull(imdbId);
    }

    @Override
    public void deliverResult(MovieFull movieFull) {
        Log.d("searchloader", "deliver result");
        super.deliverResult(movieFull);
    }
}
