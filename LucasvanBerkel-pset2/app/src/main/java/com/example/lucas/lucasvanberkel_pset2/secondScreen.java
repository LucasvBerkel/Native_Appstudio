package com.example.lucas.lucasvanberkel_pset2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.example.lucas.lucasvanberkel_pset2.R.drawable.madlibs;

public class secondScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        readText();
    }

    public void readText(){
        String[] stories = new String[5];
        stories[0] = "assets/madlib0_simple.txt";
        stories[1] = "assets/madlib1_tarzan.txt";
        stories[2] = "assets/madlib2_university.txt";
        stories[3] = "assets/madlib3_clothes.txt";
        stories[4] = "assets/madlib4_dance.txt";

        InputStream in = new FileInputStream(new File(stories[0]));
        com.example.lucas.lucasvanberkel_pset2.Story story = new com.example.lucas.lucasvanberkel_pset2.Story(in);

    }
}
