package com.example.lucas.lucasvanberkel_pset6;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.lucas.lucasvanberkel_pset6.adapter.ExpandableListAdapter;
import com.example.lucas.lucasvanberkel_pset6.api.ApiHelper;
import com.example.lucas.lucasvanberkel_pset6.classes.Item;
import com.example.lucas.lucasvanberkel_pset6.util.Util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the search-activity. Layout-wise it is almost identical to the main-activity. When activated
 * it retrieves the query of the main-activity or itself and searches the api for the query.
 * The result is shown in the same expandable listview as the favorites in the main-activity
 */
public class SearchActivity extends AppCompatActivity {

    ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<Item>> listHash;

    public final static String QUERY = "query";
    public final static String TYPE = "type";
    public final static String TITLE = "title";

    private ProgressBar mProgress;

    // Basic onCreate method, retrieves the query, sets up a progressbar, the listview and starts the query
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Results");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Search for movies, tv-series or persons", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mProgress = (ProgressBar) findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.QUERY);

        listView = (ExpandableListView)findViewById(R.id.lstExp);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        searchQuery(query);

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
    }

    // The two methods that initialises the menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

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
        }

        return super.onOptionsItemSelected(item);
    }

    // Same data initialisation as in the main-activity, same warning...
    private void initData(){
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

    // The search-method, asynctask is set up and executed.
    private void searchQuery(String query){
        mProgress.setVisibility(View.VISIBLE);
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            protected Void doInBackground(String... strings) {
                List<Item> itemList;
                String query = strings[0];
                ApiHelper apiHelper = new ApiHelper();
                for (int i = 0; i<3; i++) {
                    itemList = apiHelper.load_data_from_api(query, i);
                    listHash.put(listDataHeader.get(i), itemList);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.GONE);
            }
        };
        task.execute(query);
    }
}
