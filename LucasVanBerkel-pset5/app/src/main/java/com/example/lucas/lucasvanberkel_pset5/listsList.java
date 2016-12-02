package com.example.lucas.lucasvanberkel_pset5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class listsList extends AppCompatActivity {

    private ArrayList<String> listOfLists;
    private ArrayAdapter adapter;
    public final static String LIST = "list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listslist);

        listOfLists = new ArrayList<>();

        populatetoDoList();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfLists);

        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String list;
                list = (String) adapterView.getItemAtPosition(i);
                viewList(list);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String list;
                list = (String) adapterView.getItemAtPosition(i);
                deleteList(list);
                toastDelete();
                return true;
            }
        });

        Button button= (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoTodoList();
            }
        });
    }

    private void populatetoDoList(){
        DbHelper db = new DbHelper(this);
        listOfLists = db.getAllLists();
        Lists list = Lists.getInstance();
        for (String listName : listOfLists){
            list.addToArray(listName);
        }
    }

    private void deleteList(String list){
        printArraylist();

        DbHelper db = new DbHelper(this);
        db.deleteList(list);
        Lists lists = Lists.getInstance();
        lists.removeFromArray(list);
        listOfLists.remove(list);

        adapter.notifyDataSetChanged();
        printArraylist();
    }

    private void viewList(String list){
        Intent intent = new Intent(this, toDoList.class);
        intent.putExtra(LIST, list);
        startActivity(intent);
    }

    private void toastDelete(){
        Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT).show();
    }

    private void addtoTodoList(){
        Intent intent = new Intent(this, addList.class);
        startActivity(intent);
        finish();
    }

    private void printArraylist(){
        Log.d("Temp", "What");
        for (String listItem: listOfLists) {
            Log.d("List", listItem);
        }
    }
}
