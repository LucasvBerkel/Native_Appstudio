package nl.yrck.mprog_watchlist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import nl.yrck.mprog_watchlist.api.Movie;
import nl.yrck.mprog_watchlist.api.MovieSearchResult;
import nl.yrck.mprog_watchlist.loaders.SearchLoader;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieSearchResult> {

    ArrayList<Movie> movies;
    SearchView searchView;
    Boolean savedInstance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handleIntent(getIntent());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("MOVIES");
            savedInstance = savedInstanceState.getBoolean("SAVED_INSTANCE");
            initFragment();
        } else {
            movies = new ArrayList<>();
            initFragment();
        }
    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerMovieFragment recyclerMovieFragment = new RecyclerMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MOVIES", movies);
        recyclerMovieFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment, recyclerMovieFragment, RecyclerMovieFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MOVIES", movies);
        outState.putBoolean("SAVED_INSTANCE", true);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);
            Bundle bundle = new Bundle();
            bundle.putString("SEARCH_QUERY", searchQuery);
            getSupportLoaderManager().restartLoader(0, bundle, this);
        }
    }

    private void showError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.e("Search", "Error: " + error);
        builder.setMessage(error)
                .setTitle("Search Error")
                .setCancelable(true)
                .setNeutralButton("Dismiss", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.dismiss();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItem searchMenu = menu.findItem(R.id.action_search);

        if (!savedInstance) {
            MenuItemCompat.expandActionView(searchMenu);
        }

        return true;
    }

    @Override
    public Loader<MovieSearchResult> onCreateLoader(int id, Bundle args) {
        runOnUiThread(() -> {
            getFragmentManager().executePendingTransactions();
            RecyclerMovieFragment recyclerMovieFragment = (RecyclerMovieFragment) getFragmentManager()
                    .findFragmentByTag(RecyclerMovieFragment.TAG);
            if (recyclerMovieFragment != null) {
                recyclerMovieFragment.showProgressbar();
            }
        });
        String searchQuery = args.getString("SEARCH_QUERY");
        return new SearchLoader(this, searchQuery);
    }

    @Override
    public void onLoadFinished(Loader<MovieSearchResult> loader, MovieSearchResult data) {
        RecyclerMovieFragment recyclerMovieFragment = (RecyclerMovieFragment) getFragmentManager()
                .findFragmentByTag(RecyclerMovieFragment.TAG);
        recyclerMovieFragment.hideProgressbar();

        if (data.response()) {
            recyclerMovieFragment.refreshData(data.getMovies());
        } else {
            showError(data.getError());
        }
    }

    @Override
    public void onLoaderReset(Loader<MovieSearchResult> loader) {
        Log.d("searchactivty", "loader reset");
    }
}
