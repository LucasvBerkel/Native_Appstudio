package nl.yrck.mprog_watchlist.storage;

import java.util.Set;

public interface MovieIdSave {
    void saveMovieId(String imdbId);

    void removeMovieId(String imdbId);
    Set<String> getMovieIds();

    boolean contains(String imdbId);
}
