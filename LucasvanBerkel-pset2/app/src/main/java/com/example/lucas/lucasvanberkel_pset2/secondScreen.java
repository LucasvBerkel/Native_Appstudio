package com.example.lucas.lucasvanberkel_pset2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.util.Random;


public class secondScreen extends AppCompatActivity {

    public final static String STORY = "story";
    Story story = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        readText();
    }

    public void readText() {
        String[] stories = {"madlib0_simple.txt", "madlib1_tarzan.txt", "madlib2_university.txt", "madlib3_clothes.txt", "madlib4_dance.txt"};
        Random rn = new Random();
        int random = rn.nextInt(4);
        AssetManager assets = getAssets();
        try {
            story = new Story(assets.open(stories[random]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.secondScreenTextBox)).setHint(story.getNextPlaceholder());
        String text = Integer.toString(story.getPlaceholderRemainingCount()) + " word(s) remaining";
        ((TextView) findViewById(R.id.secondScreenText)).setText(text);

        text = "Please type a/an " + story.getNextPlaceholder();
        ((TextView) findViewById(R.id.secondScreenTextBelow)).setText(text);
    }

    public void nextHint(View view) {
        String word = ((TextView) findViewById(R.id.secondScreenTextBox)).getText().toString();
        if (!word.equals("")) {
            story.fillInPlaceholder(word);
            ((TextView) findViewById(R.id.secondScreenTextBox)).setText("");
            ((TextView) findViewById(R.id.secondScreenTextBox)).setHint(story.getNextPlaceholder());
            String text = Integer.toString(story.getPlaceholderRemainingCount()) + " word(s) remaining";
            ((TextView) findViewById(R.id.secondScreenText)).setText(text);

            text = "Please type a/an " + story.getNextPlaceholder();
            ((TextView) findViewById(R.id.secondScreenTextBelow)).setText(text);
        }

        if (story.getPlaceholderRemainingCount()==0){
            Intent intent = new Intent(this, endScreen.class);
            String message = story.toString();
            intent.putExtra(STORY, message);
            startActivity(intent);
            finish();
        }
    }
}
