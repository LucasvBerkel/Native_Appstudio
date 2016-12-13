package com.example.lucas.lucasvanberkel_pset6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.lucasvanberkel_pset6.classes.User;
import com.example.lucas.lucasvanberkel_pset6.db.FbHelper;
import com.example.lucas.lucasvanberkel_pset6.util.Util;

public class SettingsActivity extends AppCompatActivity {

    TextView usernameField;
    TextView firstnameField;
    TextView lastnameField;
    TextView addressField;

    String username;
    String firstname;
    String lastname;
    String address;

    boolean newUser;

    SharedPreferences pref;
    FbHelper fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fill in your userinformation", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        usernameField = (TextView) findViewById(R.id.settingsUsername);
        firstnameField = (TextView) findViewById(R.id.settingsFirstname);
        lastnameField = (TextView) findViewById(R.id.settingsLastname);
        addressField = (TextView) findViewById(R.id.settingsAddress);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        username = pref.getString("username", null);

        newUser = (username == null);

        fb = new FbHelper();

        if (!newUser) {
            usernameField.setText(username);
            usernameField.setEnabled(false);
        }


        Button buttonCancel = (Button) findViewById(R.id.submitButton);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(usernameField, InputMethodManager.SHOW_IMPLICIT);
    }

    private void submit() {
        username = usernameField.getText().toString();
        firstname = firstnameField.getText().toString();
        lastname = lastnameField.getText().toString();
        address = addressField.getText().toString();

        if(username.equals("") | firstname.equals("") | lastname.equals("")){
            toastSomething("Username, firstname and lastname are required");
        } else {
                fb.addUser(new User(username, firstname, lastname, address));
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", username);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
        }
    }

    private void toastSomething(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

}
