package com.example.lucas.lucasvanberkel_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class endScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        Intent intent = getIntent();
        String message = intent.getStringExtra(secondScreen.STORY);

        ((TextView) findViewById(R.id.endScreenStory)).setText(message);
    }

    public void returnToSecondScreen(View view){
        Intent intent = new Intent(this, secondScreen.class);
        startActivity(intent);
        finish();
    }
}
