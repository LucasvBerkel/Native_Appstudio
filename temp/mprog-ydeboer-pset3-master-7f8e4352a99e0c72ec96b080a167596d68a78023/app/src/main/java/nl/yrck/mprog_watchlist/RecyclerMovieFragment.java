package nl.yrck.mprog_watchlist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import nl.yrck.mprog_watchlist.api.Movie;


public class RecyclerMovieFragment extends Fragment {

    static final String TAG = "RECYLCER_MOVIE_FRAGMENT";

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    ProgressBar progressBar;

    List<Movie> movies;

    public RecyclerMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movies = getArguments().getParcelableArrayList("MOVIES");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter(movies, getActivity());
        recyclerAdapter.setOnItemClickListener((position, v) -> startMovieDetailsActivity(v));

        recyclerView.setAdapter(recyclerAdapter);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        if (getArguments().getBoolean("SHOW_PROGRESSBAR")) {
            showProgressbar();
        }

        return rootView;
    }

    public void showProgressbar() {
        if (recyclerView != null && progressBar != null) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void refreshData(List<Movie> newMovies) {
        movies.clear();
        movies.addAll(newMovies);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void startMovieDetailsActivity(View view) {
        TextView movieTitle = (TextView) view.findViewById(R.id.card_title);
        ImageView moviePoster = (ImageView) view.findViewById(R.id.card_poster);

        Bundle bundle = new Bundle();
        bundle.putString("IMDB_ID", view.getTag().toString());
        bundle.putString("MOVIE_TITLE", movieTitle.getText().toString());
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtras(bundle);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), moviePoster, "TRANS");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
