package com.example.ruben.rubengerritse_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This file describes the class BaseActivity, which is an extention off the AppCompatActivity
 * class. BaseActivity describes the settings for the option menu throught the app. Other activities
 * extend this class.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

//    Set the layout of the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

//    Navigates the user to another activity when an option from the option menu is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home_mi:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.lists_mi:
                intent = new Intent(this, ListsActivity.class);
                startActivity(intent);
                return true;
            case R.id.search_mi:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.sign_out_mi:
                intent = new Intent(this, SignInActivity.class);
                intent.putExtra("sign_out", true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    Returns the user id from firebase
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
