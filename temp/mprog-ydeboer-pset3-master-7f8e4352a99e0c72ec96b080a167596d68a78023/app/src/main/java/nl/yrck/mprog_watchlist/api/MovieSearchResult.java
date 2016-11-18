package nl.yrck.mprog_watchlist.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSearchResult {
    private List<Movie> movies;
    private int totalResults;
    private boolean response;
    private String error;

    @JsonCreator
    MovieSearchResult(
            @JsonProperty("Search") List<Movie> movies,
            @JsonProperty("totalResults") int totalResults,
            @JsonProperty("Response") boolean response,
            @JsonProperty("Error") String error) {
        this.movies = movies;
        this.totalResults = totalResults;
        this.response = response;
        this.error = error;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public boolean response() {
        return response;
    }

    public String getError() {
        return error;
    }
}
