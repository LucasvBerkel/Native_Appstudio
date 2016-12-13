package com.example.ruben.rubengerritse_pset6;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This file describes the class ListsActivity, which is an extension of the BaseActivity. It
 * allows to manage his/her lists.
 */

public class ListsActivity extends BaseActivity {

    private static final String PATH = "http://www.giantbomb.com/api/games";
    private static final String KEY = "5496ad3bff8caf2cbadfe3dbbd0bc63c2d41ff34";
    private static final String FORMAT = "json";
    private static final String FIELD_LIST = "id,name,image,original_release_date";
    private static final String RESOURCES = "game";

    private static final String TAG = "ListsActivity";
    private DatabaseReference mDatabaseUser;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private String urlStringBase;

//    Set the properties of the RecyclerView and the Spinner
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users").child(getUid());

        urlStringBase = PATH;
        urlStringBase += String.format("?api_key=%s", KEY);
        urlStringBase += String.format("&format=%s", FORMAT);
        urlStringBase += String.format("&field_list=%s", FIELD_LIST);
        urlStringBase += String.format("&resources=%s", RESOURCES);

        recyclerView = (RecyclerView) findViewById(R.id.lists_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        spinner = (Spinner) findViewById(R.id.lists_sp);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.lists, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        setSpinnerItemSelectedListener();
    }

//    Set the ItemSelectedListener for the Spinner
    private void setSpinnerItemSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       final long id) {
                String listName = ((TextView) view).getText().toString();
                DatabaseReference mListRef = mDatabaseUser.child(listName);
                mListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            GenericTypeIndicator<ArrayList<Integer>> t =
                                    new GenericTypeIndicator<ArrayList<Integer>>() {
                                    };
                            ArrayList<Integer> list = dataSnapshot.getValue(t);

                            String urlString = urlStringBase + "&filter=id:";
                            for (Integer integer : list) {
                                urlString += integer.toString() + "|";
                            }

                            try {
                                URL url = new URL(urlString);
                                String jsonString = new DatabaseQuery().execute(url).get();
                                JSONObject json = new JSONObject(jsonString);
                                if (json.getString("status_code").equals("1")) {
                                    String resultsString = json.getString("results");
                                    JSONArray resultsArray = new JSONArray(resultsString);
                                    RecyclerView.Adapter adapter = new SearchAdapter(resultsArray);
                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (MalformedURLException | InterruptedException
                                    | ExecutionException | JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
