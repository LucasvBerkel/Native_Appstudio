package nl.yrck.mprog_watchlist;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import at.favre.lib.dali.Dali;
import nl.yrck.mprog_watchlist.api.MovieFull;
import nl.yrck.mprog_watchlist.loaders.MovieFullLoader;
import nl.yrck.mprog_watchlist.storage.MovieIdSave;
import nl.yrck.mprog_watchlist.storage.MovieIdSharedPreference;


public class MovieDetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<MovieFull>,
        View.OnClickListener {

    TextView plot;
    TextView year;
    TextView genre;
    TextView director;
    TextView writer;
    TextView actors;
    TextView imdbVotes;
    TextView imdbRating;
    TextView metaScore;

    ShareActionProvider shareActionProvider;
    ImageView poster;
    ImageView toolbarPostBlur;
    Button showPlot;
    FloatingActionButton fab;
    MovieIdSave movieIdSave;
    String imdbId;
    String movieTitle;
    String plotText;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imdbId = getIntent().getExtras().getString("IMDB_ID");
        movieTitle = getIntent().getExtras().getString("MOVIE_TITLE");
        toolbar.setTitle(movieTitle);

        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        movieIdSave = new MovieIdSharedPreference(this);
        toggleFab();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plot = (TextView) findViewById(R.id.details_plot);
        year = (TextView) findViewById(R.id.details_year);
        genre = (TextView) findViewById(R.id.details_genre);
        director = (TextView) findViewById(R.id.details_director);
        writer = (TextView) findViewById(R.id.details_writer);
        actors = (TextView) findViewById(R.id.details_actors);
        imdbVotes = (TextView) findViewById(R.id.details_imdbvotes);
        imdbRating = (TextView) findViewById(R.id.details_imdbrating);
        metaScore = (TextView) findViewById(R.id.details_metascore);

        poster = (ImageView) findViewById(R.id.details_poster);
        toolbarPostBlur = (ImageView) findViewById(R.id.toolbar_poster_blur);


        showPlot = (Button) findViewById(R.id.show_plot);
        showPlot.setOnClickListener(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            poster.setTransitionName("TRANS");
        }
        getSupportLoaderManager().initLoader(0, getIntent().getExtras(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_plot:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Plot")
                        .setMessage(plotText)
                        .setNeutralButton("DISMISS", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.fab:
                if (movieIdSave.contains(imdbId)) {
                    movieIdSave.removeMovieId(imdbId);
                    Snackbar.make(v, "Removed movie", Snackbar.LENGTH_LONG);
                } else {
                    movieIdSave.saveMovieId(imdbId);
                    Snackbar.make(v, "Saved movie", Snackbar.LENGTH_LONG);
                }
                toggleFab();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }
                return true;
            case R.id.action_about:
                Utill.aboutDialog(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleFab() {
        if (movieIdSave.contains(imdbId)) {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorRemove)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_remove_white_24px));
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorAdd)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_white_24px));
        }
    }

    @Override
    public Loader<MovieFull> onCreateLoader(int id, Bundle args) {
        String imdbId = args.getString("IMDB_ID");
        Log.d("Should init loading ", imdbId);
        return new MovieFullLoader(this, imdbId);
    }

    @Override
    public void onLoadFinished(Loader<MovieFull> loader, MovieFull data) {

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                poster.setImageBitmap(bitmap);
                Bitmap blurBitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
                Dali.create(context)
                        .load(blurBitmap)
                        .blurRadius(12)
                        .downScale(2)
                        .colorFilter(Color.parseColor("#ffbdbdbd"))
                        .concurrent()
                        .into(toolbarPostBlur);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        // Only reload when not shared by previous activity
        Picasso.with(this).load(data.getPoster()).into(target);

        plotText = data.getPlot();
        if (plotText.length() > 200) {
            plot.setText(plotText.substring(0, 200) + "...");
        } else {
            plot.setText(plotText);
        }

        year.setText(data.getTitle() + " - " + data.getYear());
        genre.setText("Genre: " + data.getGenre());
        director.setText("Director: " + data.getDirector());
        writer.setText("Writers: " + data.getWriters());
        actors.setText("Actors: " + data.getActors());
        imdbVotes.setText("Imdb votes: " + data.getImdbvotes());
        imdbRating.setText("Imdb rating: " + data.getImdbrating());
        metaScore.setText("Metascore: " + data.getMetascore());
    }

    @Override
    public void onLoaderReset(Loader<MovieFull> loader) {
        Log.d("searchactivty", "loader reset");
    }

}
