package com.example.lucas.lucasvanberkel_pset5;

import java.util.ArrayList;

public class Lists {
    ArrayList<String> lists;

    private static Lists ourInstance = new Lists();

    public static Lists getInstance() {
        return ourInstance;
    }

    private Lists() {
        lists = new ArrayList<>();
    }

    protected void addToArray(String list){
        lists.add(list);
    }

    protected void removeFromArray(String list){
        lists.remove(list);
    }

    protected ArrayList<String> getArray(){
        return lists;
    }

}
