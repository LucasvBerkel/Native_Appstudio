package com.example.lucas.lucasvanberkel_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class startScreen extends AppCompatActivity {

    public final static String SEARCH = "search";

    boolean visibility;
    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        searchText = ((TextView) findViewById(R.id.searchBar)).getText().toString();
        savedInstanceState.putBoolean("VISIBILITY", visibility);
        savedInstanceState.putString("TEXT", searchText);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        visibility = savedInstanceState.getBoolean("VISIBILITY");
        searchText = savedInstanceState.getString("TEXT");
        if (visibility) {
            findViewById(R.id.searchBar).setVisibility(View.VISIBLE);
            findViewById(R.id.searchButton).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.searchBar)).setText(searchText);
        }
    }

    public void startSearch(View view) {
        findViewById(R.id.searchBar).setVisibility(View.VISIBLE);
        findViewById(R.id.searchButton).setVisibility(View.VISIBLE);
        visibility = true;
        searchText = ((TextView) findViewById(R.id.searchBar)).getText().toString();
    }

    public void search(View view) {
        searchText = ((TextView) findViewById(R.id.searchBar)).getText().toString();

        Intent intent = new Intent(this, searchList.class);
        intent.putExtra(SEARCH, searchText);
        startActivity(intent);
    }
}
