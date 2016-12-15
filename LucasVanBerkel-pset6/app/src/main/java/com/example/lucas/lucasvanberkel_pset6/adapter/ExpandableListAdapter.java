package com.example.lucas.lucasvanberkel_pset6.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucas.lucasvanberkel_pset6.R;
import com.example.lucas.lucasvanberkel_pset6.classes.Item;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * This activity builds the expandable adapter.
 * Source of the code is
 * From https://www.youtube.com/watch?v=jZxZIFnJ9jE
 * But the code modified for the use of the app
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Item>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Item>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    // Method to initialise a parent in the expandable listview
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        headerTitle = headerTitle + " (" + Integer.toString(getChildrenCount(i)) + ")";
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, viewGroup, false);
        }
        TextView lblListHeader = (TextView)view.findViewById(R.id.lstGroupHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    // Method to initialise a child in the expandable listview
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Item item = (Item)getChild(i, i1);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }
        TextView txtListChild = (TextView)view.findViewById(R.id.lstItem);
        final String childText = item.getName();
        txtListChild.setText(childText);

        TextView txtExtraListChild = (TextView)view.findViewById(R.id.lstItemExtra);
        final String childExtraText = item.getExtra();
        txtExtraListChild.setText(childExtraText);

        ImageView imgListChild = (ImageView)view.findViewById(R.id.lstImg);
        imgListChild.setImageResource(R.drawable.no_poster);

        Context context = viewGroup.getContext();

        if (!(item.getUrl().equals("null") | item.getUrl().equals("0"))) {
            String link = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + item.getUrl();
            Picasso.with(context).load(link).into(imgListChild);
        }
        return view;
    }

    // Set to true to enable selecting the child(item)
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
