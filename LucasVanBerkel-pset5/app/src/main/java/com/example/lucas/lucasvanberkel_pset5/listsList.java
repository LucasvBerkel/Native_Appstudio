package com.example.lucas.lucasvanberkel_pset5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                Todo toDo;
                toDo = (Todo) adapterView.getItemAtPosition(i);
                updateTodo(toDo);
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
        Lists lists = Lists.getInstance();
        listOfLists = lists.getArray();
    }

    private void deleteList(String list){
        Lists lists = Lists.getInstance();
        lists.removeFromArray(list);
        listOfLists = lists.getArray();
        adapter.notifyDataSetChanged();
    }

    private void updateTodo(Todo upToDo) {
        DbHelper db = new DbHelper(this);
        if (upToDo.status == 0){
            upToDo.setStatus(1);
            db.updateToDo(upToDo);
        } else{
            upToDo.setStatus(0);
            db.updateToDo(upToDo);
        }
        adapter.notifyDataSetChanged();
    }

    private void toastDelete(){
        Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT).show();
    }

    private void addtoTodoList(){
    }
}
