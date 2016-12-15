package com.example.lucas.lucasvanberkel_pset6.classes;

/**
 * Simple class used to easily separate data of item of eachother
 */
public class Item {

    private String name;
    private String url;
    private String extra;
    private int type;
    private int id;

    public Item(String name, String url, String extra, int type, int id) {
        this.name = name;
        this.url = url;
        this.extra = extra;
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
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
}
