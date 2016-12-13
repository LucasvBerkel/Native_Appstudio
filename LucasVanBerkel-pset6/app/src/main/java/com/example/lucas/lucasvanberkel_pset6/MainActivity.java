package com.example.lucas.lucasvanberkel_pset6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.lucas.lucasvanberkel_pset6.adapter.ExpandableListAdapter;
import com.example.lucas.lucasvanberkel_pset6.classes.Item;
import com.example.lucas.lucasvanberkel_pset6.db.DbHelper;
import com.example.lucas.lucasvanberkel_pset6.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<Item>> listHash;

    public final static String QUERY = "query";
    public final static String TYPE = "type";
    public final static String TITLE = "title";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String username = pref.getString("username", null);

        if (username == null) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            finish();
        } else {
            toolbar.setTitle("Hello " + username + "!");
            Log.d("Tag", username);
            Log.d("Hello", "bye");
        }

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

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Item item;
                item = (Item) listAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(getApplicationContext(), IndividualActivity.class);
                intent.putExtra(QUERY, item.getId());
                intent.putExtra(TYPE, item.getType());
                intent.putExtra(TITLE, item.getName());
                startActivity(intent);
                return true;
            }
        });

        retrieveFav();
    }

    public void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Movies");
        listDataHeader.add("Series");
        listDataHeader.add("Persons");

        for(int i = 0; i < 3; i++){
            List<Item> itemList = new ArrayList<>();
            listHash.put(listDataHeader.get(i), itemList);
        }
    }

    private void retrieveFav(){
        DbHelper db = new DbHelper(this);

        List<Item> itemList;
        for (int i = 0; i<3; i++) {
            itemList = db.getAllFavorites(i);
            listHash.put(listDataHeader.get(i), itemList);
        }
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
                Util.aboutDialog(this);
                return true;
            case R.id.action_refresh:
                retrieveFav();
                listAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
