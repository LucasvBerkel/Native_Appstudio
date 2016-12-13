package com.example.lucas.lucasvanberkel_pset6.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lucas.lucasvanberkel_pset6.classes.Item;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Favorites";
    private static final String TABLE_NAME = "favoriteList";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_API_ID = "api_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_EXTRA = "extra";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_URL + " TEXT, " +
                    COLUMN_API_ID + " INTEGER," +
                    COLUMN_TYPE + " INTEGER," +
                    COLUMN_EXTRA + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Item> getAllFavorites(int type){
        ArrayList<Item> list = new ArrayList<>();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, new String[] {Integer.toString(type)});

        if(c.moveToFirst()) {
            do {
                list.add(new Item(c.getString(c.getColumnIndex(COLUMN_NAME)),
                                  c.getString(c.getColumnIndex(COLUMN_URL)),
                                  c.getString(c.getColumnIndex(COLUMN_EXTRA)),
                                  c.getInt(c.getColumnIndex(COLUMN_TYPE)),
                                  c.getInt(c.getColumnIndex(COLUMN_API_ID))));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_URL, item.getUrl());
        values.put(COLUMN_API_ID, item.getId());
        values.put(COLUMN_TYPE, item.getType());
        values.put(COLUMN_EXTRA, item.getExtra());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void deleteItem(Item delItem){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + " = ? AND " + COLUMN_API_ID + " = ?", new String[] {delItem.getName(), Integer.toString(delItem.getId())});
        db.close();
    }

    public boolean checkIfFav(int api_id){
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_API_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, new String[] {Integer.toString(api_id)});

        if(c.getCount() <= 0){
            c.close();
            db.close();
            return false;
        }
        c.close();
        db.close();
        return true;
    }
}
