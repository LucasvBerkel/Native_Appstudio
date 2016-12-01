package com.example.lucas.lucasvanberkel_pset5;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class toDoList extends AppCompatActivity {

    private ArrayList<Todo> toDoList;
    private String addText;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        toDoList = new ArrayList<>();

        populatetoDoList();

        adapter = new MyAdapter(this, toDoList);

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
                Todo toDo;
                toDo = (Todo) adapterView.getItemAtPosition(i);
                deleteTodo(toDo);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        addText = ((TextView) findViewById(R.id.addEditText)).getText().toString();
        savedInstanceState.putString("TEXT", addText);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        addText = savedInstanceState.getString("TEXT");
        ((TextView) findViewById(R.id.addEditText)).setText(addText);
    }

    private void populatetoDoList(){
        DbHelper db = new DbHelper(this);
        toDoList = db.getAllToDo();
    }

    private void addtoTodoList() {
        addText = ((TextView) findViewById(R.id.addEditText)).getText().toString();
        if (addText.equals("")) {
            Toast.makeText(this, "Fill in a to-do item", Toast.LENGTH_SHORT).show();
        } else {
            Todo newTodo = new Todo(addText, 0);
            DbHelper db = new DbHelper(this);
            db.addTodo(newTodo);
            toDoList.add(newTodo);
            adapter.notifyDataSetChanged();
            hideSoftKeyboard(toDoList.this);
            ((TextView) findViewById(R.id.addEditText)).setText("");
            ((TextView) findViewById(R.id.addEditText)).setHint("Add a new to-do item");
        }
    }

    private void deleteTodo(Todo delToDo){
        DbHelper db = new DbHelper(this);
        db.deleteTodo(delToDo);
        toDoList.remove(delToDo);
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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
