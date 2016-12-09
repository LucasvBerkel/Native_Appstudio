package com.example.lucas.lucasvanberkel_pset6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

// From https://www.youtube.com/watch?v=jZxZIFnJ9jE
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

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        headerTitle = headerTitle + " (" + Integer.toString(getChildrenCount(i)) + ")";
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }
        TextView lblListHeader = (TextView)view.findViewById(R.id.lstGroupHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Item item = (Item)getChild(i, i1);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
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
//
//            String link = "https://image.tmdb.org/t/p/w90_and_h90_bestv2" + item.getUrl();
//            URL url = null;
//            try {
//                url = new URL(link);
//                Bitmap bmp = new ImageDownload().execute(url).get();
//                imgListChild.setImageBitmap(bmp);
//            } catch (MalformedURLException | InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
