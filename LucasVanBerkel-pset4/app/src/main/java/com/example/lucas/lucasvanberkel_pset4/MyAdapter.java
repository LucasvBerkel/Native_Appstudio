package com.example.lucas.lucasvanberkel_pset4;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Todo> {
    public MyAdapter(Context context, ArrayList<Todo> toDoList) {
        super(context, 0, toDoList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Todo toDo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView todoItem = (TextView) convertView.findViewById(R.id.todo);

        todoItem.setText(toDo.todo);

        ImageView vinkje = (ImageView) convertView.findViewById(R.id.vinkje);

        if(toDo.getStatus() == 1){
            vinkje.setVisibility(View.VISIBLE);
        }

        if (toDo.getStatus() == 0){
            vinkje.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}