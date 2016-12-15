package com.example.lucas.lucasvanberkel_pset6;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lucas.lucasvanberkel_pset6.api.ApiHelper;
import com.example.lucas.lucasvanberkel_pset6.classes.ItemExtended;
import com.example.lucas.lucasvanberkel_pset6.db.DbHelper;
import com.example.lucas.lucasvanberkel_pset6.util.Util;
import com.squareup.picasso.Picasso;

/**
 * This activity shows a individual item and extends it with additional information. The activity is
 * accessible from both the main-activity as the searc-activity.
 */
public class IndividualActivity extends AppCompatActivity {

    ImageView poster;
    TextView title;
    TextView year;
    TextView extra;
    TextView summary;

    ItemExtended item;
    boolean fav;

    private ProgressBar mProgress;
    private FloatingActionButton fab;

    // Basic onCreate method, finds the views inside the activity and starts the query
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        int query = intent.getIntExtra(SearchActivity.QUERY, 0);
        int type = intent.getIntExtra(SearchActivity.TYPE, 0);
        toolbar.setTitle(intent.getStringExtra(SearchActivity.TITLE));

        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFav();
            }
        });

        mProgress = (ProgressBar) findViewById(R.id.progress_bar);


        searchIndiQuery(query, type);

        poster = (ImageView) findViewById (R.id.poster);
        title = (TextView) findViewById(R.id.title);
        year = (TextView) findViewById(R.id.year);
        extra = (TextView) findViewById(R.id.extra);
        summary = (TextView) findViewById(R.id.summary);
    }

    // The two methods that initialises the menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_indi, menu);
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
    // Method to check if current selected item is already favorite of the user
    private void checkIfFavorite() {
        DbHelper db = new DbHelper(this);

        if(db.checkIfFav(item.getId())){
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorButtonFav)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_off));
            fav = true;
        } else {
            fav = false;
        }
    }

    // Method to change the status of the item to favorite/not-favorite and changes the colors of the floating button
    private void changeFav() {
        DbHelper db = new DbHelper(this);
        if(fav){
            db.deleteItem(item);
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorButtonNot)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_off));
            fav = false;
        } else {
            db.addItem(item);
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorButtonFav)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_on));
            fav = true;
        }
    }

    // Method to search the item in the api, comparable with searchquery in the search-activity
    private void searchIndiQuery(int query, int type) {
        mProgress.setVisibility(View.VISIBLE);
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            protected Void doInBackground(Integer... params) {
                int query = params[0];
                int type = params[1];
                ApiHelper apiHelper = new ApiHelper();
                item = apiHelper.load_data_from_api_indi(query, type);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                fillInViews();
                mProgress.setVisibility(View.GONE);
                checkIfFavorite();
            }
        };
        task.execute(query, type);
    }

    // Method to fill in the found data, comparable with adapter.notifyDataSetChanged() in the search-activity
    private void fillInViews() {
        String titleString = item.getName();
        String yearString = item.getExtra();
        String extraString = item.getExtra1();
        String summaryString = item.getExtra2();

        title.setText(titleString);
        year.setText(yearString);
        extra.setText(extraString);
        summary.setText(summaryString);


        String link = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + item.getUrl();
        Picasso.with(getApplicationContext()).load(link).into(poster);
    }
}
