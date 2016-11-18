package nl.yrck.mprog_watchlist.api;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.NumberFormat;
import java.text.ParseException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieFull extends Movie {

    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writers;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private int metascore;
    private float imdbrating;
    private int imdbvotes;

    @JsonCreator
    public MovieFull(@JsonProperty("Title") String title,
                     @JsonProperty("Year") String year,
                     @JsonProperty("imdbID") String imdbID,
                     @JsonProperty("Type") String type,
                     @JsonProperty("Poster") String poster,
                     @JsonProperty("Rated") String rated,
                     @JsonProperty("Released") String released,
                     @JsonProperty("Runtime") String runtime,
                     @JsonProperty("Genre") String genre,
                     @JsonProperty("Director") String director,
                     @JsonProperty("Writer") String writers,
                     @JsonProperty("Actors") String actors,
                     @JsonProperty("Plot") String plot,
                     @JsonProperty("Language") String language,
                     @JsonProperty("Country") String country,
                     @JsonProperty("Awards") String awards,
                     @JsonProperty("Metascore") String metascore,
                     @JsonProperty("imdbRating") float imdbrating,
                     @JsonProperty("imdbVotes") String imdbvotes
    ) {
        super(title, year, imdbID, type, poster);
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writers = writers;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.metascore = parseStringInt(metascore);
        this.imdbrating = imdbrating;
        this.imdbvotes = parseCommaInt(imdbvotes);
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriters() {
        return writers;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public int getMetascore() {
        return metascore;
    }

    public float getImdbrating() {
        return imdbrating;
    }

    public int getImdbvotes() {
        return imdbvotes;
    }

    private int parseStringInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int parseCommaInt(String string) {
        try {
            return NumberFormat.getNumberInstance(java.util.Locale.US).parse(string).intValue();
        } catch (ParseException e) {
            Log.e("Parse exception", "" + e);
        }
        return -1;
    }
}
