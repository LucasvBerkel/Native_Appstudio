package com.example.lucas.lucasvanberkel_pset5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addList extends AppCompatActivity {
    String addText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        Button button= (Button) findViewById(R.id.addButtonList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSingleton();
            }
        });

        Button buttonCancel= (Button) findViewById(R.id.cancelButton);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


        EditText editText = (EditText)findViewById(R.id.addListText);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void addToSingleton(){
        addText = ((TextView) findViewById(R.id.addListText)).getText().toString();
        Lists list = Lists.getInstance();
        if (addText.equals("")) {
            Toast.makeText(this, "Fill in a new list name", Toast.LENGTH_SHORT).show();
        } else if(list.stringInArray(addText)) {
            Toast.makeText(this, "List name already exists", Toast.LENGTH_SHORT).show();
        } else {
            list.addToArray(addText);
            DbHelper db = new DbHelper(this);
            db.addList(addText);
            Intent intent = new Intent(this, listsList.class);
            startActivity(intent);
            finish();
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, listsList.class);
        startActivity(intent);
        finish();
    }
}
