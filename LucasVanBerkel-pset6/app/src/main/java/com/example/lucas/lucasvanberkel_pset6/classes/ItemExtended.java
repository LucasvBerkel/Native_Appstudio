package com.example.lucas.lucasvanberkel_pset6.classes;

public class ItemExtended extends Item {

    private String extra1;
    private String extra2;

    public ItemExtended(String name, String url, String extra, int type, int id, String extra1, String extra2) {
        super(name, url, extra, type, id);
        this.extra1 = extra1;
        this.extra2 = extra2;
    }

    public String getExtra1() {
        return extra1;
    }

    public String getExtra2() {
        return extra2;
    }
}
