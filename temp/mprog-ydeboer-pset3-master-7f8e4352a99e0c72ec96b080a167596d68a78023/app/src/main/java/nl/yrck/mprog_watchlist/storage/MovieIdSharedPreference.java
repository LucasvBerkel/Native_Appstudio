package nl.yrck.mprog_watchlist.storage;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class MovieIdSharedPreference implements MovieIdSave {

    private final String PREF_KEY = "movie_save";
    AppCompatActivity activity;
    SharedPreferences sharedPref;

    public MovieIdSharedPreference(AppCompatActivity activity) {
        this.activity = activity;
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
    }


    @Override
    public void saveMovieId(String imdbId) {
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> set = new HashSet<>(sharedPref.getStringSet(PREF_KEY, new HashSet<>()));
        set.add(imdbId);
        editor.putStringSet(PREF_KEY, set);
        editor.apply();
    }

    @Override
    public void removeMovieId(String imdbId) {
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> set = new HashSet<>(sharedPref.getStringSet(PREF_KEY, new HashSet<>()));
        set.remove(imdbId);
        editor.putStringSet(PREF_KEY, set);
        editor.apply();
    }

    @Override
    public Set<String> getMovieIds() {
        Set<String> set = sharedPref.getStringSet(PREF_KEY, new HashSet<>());

        return set;
    }

    @Override
    public boolean contains(String imdbId) {
        Set<String> set = sharedPref.getStringSet(PREF_KEY, new HashSet<>());
        return set.contains(imdbId);
    }
}
