package com.example.lucas.lucasvanberkel_pset3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Movie> {
    public MyAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView movieTitle = (TextView) convertView.findViewById(R.id.movieTitleSearch);

        movieTitle.setText(movie.title);

        return convertView;
    }
}
