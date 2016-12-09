package com.example.lucas.lucasvanberkel_pset6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<Item>> listHash;

    public final static String QUERY = "query";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Search for movies, tv-series or persons", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ExpandableListView)findViewById(R.id.lstExp);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();


        listDataHeader.add("Movies");
        listDataHeader.add("Series");
        listDataHeader.add("Persons");

        List<Item> movies = new ArrayList<>();
        movies.add(new Item("Bourne", "0","0", false));

        List<Item> series = new ArrayList<>();
        series.add(new Item("Breaking Bad", "0","0", false));
        series.add(new Item("Game of Thrones", "0","0", false));
        series.add(new Item("Hello kittie", "0","0", false));
        series.add(new Item("Watskeburt", "0","0", false));

        List<Item> persons = new ArrayList<>();
        persons.add(new Item("Brad Pitt", "0", "0", false));
        persons.add(new Item("George Clooney", "0","0", false));
        persons.add(new Item("Matt Damon", "0","0", false));

        listHash.put(listDataHeader.get(0), movies);
        listHash.put(listDataHeader.get(1), series);
        listHash.put(listDataHeader.get(2), persons);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Retrieve the SearchView and plug it into SearchManager
        // From: http://stackoverflow.com/questions/34825104/add-search-icon-on-action-bar-android
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query){
                searchQuery(query);
                return false;
            }

            public boolean onQueryTextChange(String query){
                return false;
            }
        });
        return true;
    }

    private  void searchQuery(String query){
        if (query.equals("")){
            toastSomething("Cannot search an empty string");
        } else{
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra(QUERY, query);
            startActivity(intent);
        }
    }

    private void toastSomething(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_about:
                aboutDialog(this);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void aboutDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("By Lucas van Berkel")
                .setTitle("About")
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
