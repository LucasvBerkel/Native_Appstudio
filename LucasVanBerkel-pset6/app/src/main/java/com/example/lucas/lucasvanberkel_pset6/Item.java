package com.example.lucas.lucasvanberkel_pset6;

public class Item {

    private String name;
    private String url;
    private String extra;
    private boolean favorite;

    public Item(String name, String url, String extra, boolean favorite) {
        this.name = name;
        this.url = url;
        this.extra = extra;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public String getExtra() {
        return extra;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
