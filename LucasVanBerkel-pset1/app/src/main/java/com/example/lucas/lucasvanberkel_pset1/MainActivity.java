package com.example.lucas.lucasvanberkel_pset1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    boolean check[] = new boolean[11];
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            check = savedInstanceState.getBooleanArray("CHECK");
            count = savedInstanceState.getInt("COUNT");
        } else {
            count = 0;
            check[0] = true;
            for (int i = 1; i < 11; i++) {
                check[i] = false;
            }
        }
        checkCheckBoxes();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current state
        savedInstanceState.putBooleanArray("CHECK", check);
        savedInstanceState.putInt("COUNT", count);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        String visibility = "INVISIBLE";
        if (checked){
            visibility = "VISIBLE";
        }
        switch (view.getId()){
            case R.id.BodyBox:
                changeVisibility(findViewById(R.id.body), visibility, 0);
                break;
            case R.id.ArmsBox:
                changeVisibility(findViewById(R.id.arms), visibility, 1);
                break;
            case R.id.EarsBox:
                changeVisibility(findViewById(R.id.ears), visibility, 2);
                break;
            case R.id.EyebrowsBox:
                changeVisibility(findViewById(R.id.eyebrows), visibility, 3);
                break;
            case R.id.EyesBox:
                changeVisibility(findViewById(R.id.eyes), visibility, 4);
                break;
            case R.id.GlassesBox:
                changeVisibility(findViewById(R.id.glasses), visibility, 5);
                break;
            case R.id.HatBox:
                changeVisibility(findViewById(R.id.hat), visibility, 6);
                break;
            case R.id.MouthBox:
                changeVisibility(findViewById(R.id.mouth), visibility, 7);
                break;
            case R.id.MustacheBox:
                changeVisibility(findViewById(R.id.mustache), visibility, 8);
                break;
            case R.id.NoseBox:
                changeVisibility(findViewById(R.id.nose), visibility, 9);
                break;
            case R.id.ShoesBox:
                changeVisibility(findViewById(R.id.shoes), visibility, 10);
                break;
        }
    }

    public void changeVisibility(View view, String visibility, int i){
        if(visibility.equals("VISIBLE")){
            view.setVisibility(View.VISIBLE);
            check[i] = true;
        } else {
            view.setVisibility(View.INVISIBLE);
            check[i] = false;
        }
    }

    public void checkCheckBoxes(){
        count ++;
        if (check[0]){
            changeVisibility(findViewById(R.id.body), "VISIBLE", 0);
        } else {
            changeVisibility(findViewById(R.id.body), "INVISIBLE", 0);
        }

        if (check[1]){
            changeVisibility(findViewById(R.id.arms), "VISIBLE", 1);
        } else {
            changeVisibility(findViewById(R.id.arms), "INVISIBLE", 1);
        }

        if (check[2]){
            changeVisibility(findViewById(R.id.ears), "VISIBLE", 2);
        } else {
            changeVisibility(findViewById(R.id.ears), "INVISIBLE", 2);
        }

        if (check[3]){
            changeVisibility(findViewById(R.id.eyebrows), "VISIBLE", 3);
        } else {
            changeVisibility(findViewById(R.id.eyebrows), "INVISIBLE", 3);
        }

        if (check[4]){
            changeVisibility(findViewById(R.id.eyes), "VISIBLE", 4);
        } else {
            changeVisibility(findViewById(R.id.eyes), "INVISIBLE", 4);
        }

        if (check[5]){
            changeVisibility(findViewById(R.id.glasses), "VISIBLE", 5);
        } else {
            changeVisibility(findViewById(R.id.glasses), "INVISIBLE", 5);
        }

        if (check[6]){
            changeVisibility(findViewById(R.id.hat), "VISIBLE", 6);
        } else {
            changeVisibility(findViewById(R.id.hat), "INVISIBLE", 6);
        }

        if (check[7]){
            changeVisibility(findViewById(R.id.mouth), "VISIBLE", 7);
        } else {
            changeVisibility(findViewById(R.id.mouth), "INVISIBLE", 7);
        }

        if (check[8]){
            changeVisibility(findViewById(R.id.mustache), "VISIBLE", 8);
        } else {
            changeVisibility(findViewById(R.id.mustache), "INVISIBLE", 8);
        }

        if (check[9]){
            changeVisibility(findViewById(R.id.nose), "VISIBLE", 9);
        } else {
            changeVisibility(findViewById(R.id.nose), "INVISIBLE", 9);
        }

        if (check[10]){
            changeVisibility(findViewById(R.id.shoes), "VISIBLE", 10);
        } else {
            changeVisibility(findViewById(R.id.shoes), "INVISIBLE", 10);
        }
    }
}
