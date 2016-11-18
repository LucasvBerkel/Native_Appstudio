package nl.yrck.mprog_watchlist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.yrck.mprog_watchlist.api.Movie;
import nl.yrck.mprog_watchlist.loaders.MoviesLoader;
import nl.yrck.mprog_watchlist.storage.MovieIdSave;
import nl.yrck.mprog_watchlist.storage.MovieIdSharedPreference;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("MOVIES");
            initFragment(false);
        } else {
            movies = new ArrayList<>();
            initFragment(true);
            getMovies();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MOVIES", movies);
        super.onSaveInstanceState(outState);
    }

    private void initFragment(boolean progressbar) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerMovieFragment recyclerMovieFragment = new RecyclerMovieFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MOVIES", movies);
        bundle.putBoolean("SHOW_PROGRESSBAR", progressbar);
        recyclerMovieFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fragment, recyclerMovieFragment, RecyclerMovieFragment.TAG);
        fragmentTransaction.commit();
    }

    private void getMovies() {
        MovieIdSave movieIdSave = new MovieIdSharedPreference(this);
        Set<String> set = movieIdSave.getMovieIds();
        String[] movie_ids = set.toArray(new String[set.size()]);

        Bundle bundle = new Bundle();
        bundle.putStringArray("MOVIE_ID_S", movie_ids);

        getSupportLoaderManager().restartLoader(0, bundle, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Utill.aboutDialog(this);
                return true;
            case R.id.action_search:
                startSearchActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        String[] movie_id_s = args.getStringArray("MOVIE_ID_S");
        return new MoviesLoader(this, movie_id_s);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        RecyclerMovieFragment recyclerMovieFragment = (RecyclerMovieFragment) getFragmentManager()
                .findFragmentByTag(RecyclerMovieFragment.TAG);
        recyclerMovieFragment.hideProgressbar();
        recyclerMovieFragment.refreshData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        Log.d("searchactivty", "loader reset");
    }
}
