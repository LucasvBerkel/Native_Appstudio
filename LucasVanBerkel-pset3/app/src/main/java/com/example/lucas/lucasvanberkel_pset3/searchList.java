package com.example.lucas.lucasvanberkel_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class searchList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent intent = getIntent();
        String searchquery = intent.getStringExtra(startScreen.SEARCH);

        ListView lv = (ListView) findViewById(R.id.searchListview);
    }

    public void stopLoading(View view){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }
}
