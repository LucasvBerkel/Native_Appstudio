package com.example.lucas.lucasvanberkel_pset1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.BodyBox:
                if(checked){
                    changeVisibility(findViewById(R.id.body), "VISIBLE");
                } else {
                    changeVisibility(findViewById(R.id.body), "INVISIBLE");
                }
                break;
        }
    }

    public void changeVisibility(View view, String visibility){
        if(visibility == "VISIBLE"){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
