package com.example.lucas.lucasvanberkel_pset3;

public class Movie{
    public String id;
    public String title;
    public String link;
    public String year;

    public Movie(String id, String title, String link, String year) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.year = year;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.title = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getYear() {
        return year;
    }

}
