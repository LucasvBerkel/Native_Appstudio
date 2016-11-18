package com.example.lucas.lucasvanberkel_pset3;

public class Movie{
    public String id;
    public String title;
    public  String link;

    public Movie(String id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
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
}
