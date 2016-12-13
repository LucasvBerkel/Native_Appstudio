package com.example.ruben.rubengerritse_pset6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This file describes the class HomeAcivity, which is an extension of the BaseActivity.
 * HomeActivity contains a describtion of the app and allows the user to navigate to other
 * activities.
 */

public class HomeActivity extends BaseActivity {

//    Sets the description the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        TextView description = (TextView) findViewById(R.id.description_tv);
        String username = pref.getString("user", null);

        if (username != null) {
            description.setText("Hello " + username + "! " + getString(R.string.description));
        }
    }

//    Go to the lists activity on click
    public void toLists(View view) {
        Intent intent = new Intent(this, ListsActivity.class);
        startActivity(intent);
    }

//    Go to the search activity on click
    public void toSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

//    Logs out on click
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}