package com.example.ruben.rubengerritse_pset6;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This file describes the GameInfoActivity, which is an extension of the BaseActivity class.
 * GameInfoActivity shows the user the information of a single game and allows the user to add or
 * remove the game from his lists.
 */

public class GameInfoActivity extends BaseActivity {

//    Url parameters
    private static final String PATH = "http://www.giantbomb.com/api/game";
    private static final String KEY = "5496ad3bff8caf2cbadfe3dbbd0bc63c2d41ff34";
    private static final String FORMAT = "json";
    private static final String FIELD_LIST = "id,name,image,deck";

//    List names
    private static final String PLAN_LIST = "Plan to Play";
    private static final String COMPLETED_LIST = "Completed";

//    Log tag
    private static final String TAG = "GameInfoActivity";

    private JSONObject gameJson;
    private DatabaseReference mDatabaseUser;
    private int gameId;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);
        gameId = getIntent().getExtras().getInt("id");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users").child(getUid());
        getJson();
        updateUI();
        setSpinnerAdapter();
    }

//    Gets the json object of the game from the api
    private void getJson() {
        String urlString = PATH;
        urlString += String.format("/%s", String.valueOf(gameId));
        urlString += String.format("?api_key=%s", KEY);
        urlString += String.format("&format=%s", FORMAT);
        urlString += String.format("&field_list=%s", FIELD_LIST);

        try {
            URL url = new URL(urlString);
            String jsonString = new DatabaseQuery().execute(url).get();
            JSONObject json = new JSONObject(jsonString);
            if (json.getString("status_code").equals("1")) {
                String resultsString = json.getString("results");
                gameJson = new JSONObject(resultsString);
            }
        } catch (MalformedURLException | InterruptedException | ExecutionException |
                JSONException e) {
            e.printStackTrace();
        }
    }

//    Updates the information of the game on the screen
    private void updateUI () {
        TextView gameName = (TextView) findViewById(R.id.game_name_tv);
        TextView gameDeck = (TextView) findViewById(R.id.game_deck_tv);
        ImageView gameImage = (ImageView) findViewById(R.id.game_image_iv);

        try {
            gameName.setText(gameJson.getString("name"));

            String imageString = gameJson.getJSONObject("image")
                    .getString("medium_url");
            URL imageUrl = new URL(imageString.replaceAll("\\/", "/"));
            Bitmap bmp = new ImageDownload().execute(imageUrl).get();
            gameImage.setImageBitmap(bmp);
            gameDeck.setText(gameJson.getString("deck"));

        } catch (JSONException | InterruptedException | MalformedURLException |
                ExecutionException e) {
            e.printStackTrace();
        }
    }

//    Sets the adapter for the spinner
    private void setSpinnerAdapter() {
        spinner = (Spinner) findViewById(R.id.game_status_sp);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.game_statuses, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

//    Changes the status of the game for the user
    public void applyGameStatus(View view) {
        String status = spinner.getSelectedItem().toString();

        switch (status) {
            case "No Status":
                removeFromList(PLAN_LIST);
                removeFromList(COMPLETED_LIST);
                break;
            case PLAN_LIST:
                addToList(PLAN_LIST);
                removeFromList(COMPLETED_LIST);
                break;
            case COMPLETED_LIST:
                addToList(COMPLETED_LIST);
                removeFromList(PLAN_LIST);
                break;
        }
    }

//    Adds an item to a list
    private void addToList(String listName) {
        final DatabaseReference mListRef = mDatabaseUser.child(listName);
        mListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Integer> list;
                if (dataSnapshot.hasChildren()) {
                    GenericTypeIndicator<ArrayList<Integer>> t =
                            new GenericTypeIndicator<ArrayList<Integer>>() {};
                    list = dataSnapshot.getValue(t);
                    if (!list.contains(gameId)) {
                        list.add(gameId);
                    }
                } else {
                    list = new ArrayList<>();
                    list.add(gameId);
                }
                mListRef.setValue(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

//    Removes an item from a list
    private void removeFromList(final String listName) {
        final DatabaseReference mListRef = mDatabaseUser.child(listName);
        mListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Integer> list;
                if (dataSnapshot.hasChildren()) {
                    GenericTypeIndicator<ArrayList<Integer>> t =
                            new GenericTypeIndicator<ArrayList<Integer>>() {};
                    list = dataSnapshot.getValue(t);
                    list.remove(Integer.valueOf(gameId));
                    mListRef.setValue(list);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
