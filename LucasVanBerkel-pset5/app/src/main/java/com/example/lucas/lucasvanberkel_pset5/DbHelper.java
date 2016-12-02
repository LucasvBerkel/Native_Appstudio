package com.example.lucas.lucasvanberkel_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "ToDoLists";
    private static final String TABLE_NAME = "Table_ToDo";
    private static final String TABLE_NAME_LIST = "Table_list";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM = "item";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_LISTNAME = "listname";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_ITEM + " TEXT," + COLUMN_STATUS + " INTEGER, " + COLUMN_LISTNAME + " TEXT" + " )";

    private static final String SQL_CREATE_TABLE_LIST = "CREATE TABLE " + TABLE_NAME_LIST + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_ITEM + " TEXT)";


    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE_LIST);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST);
        onCreate(db);
    }

    ArrayList<Todo> getAllToDo(String listname){
        ArrayList<Todo> list = new ArrayList<>();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_LISTNAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, new String[] {listname});

        if(c.moveToFirst()) {
            do {
                list.add(new Todo(c.getString(c.getColumnIndex(COLUMN_ITEM)),
                        c.getInt(c.getColumnIndex(COLUMN_STATUS))));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    ArrayList<String> getAllLists(){
        ArrayList<String> list = new ArrayList<>();
        String query = "Select * FROM " + TABLE_NAME_LIST;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                list.add(c.getString(c.getColumnIndex(COLUMN_ITEM)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    void addTodo(Todo newToDo, String listname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, newToDo.todo);
        values.put(COLUMN_STATUS, newToDo.status);
        values.put(COLUMN_LISTNAME, listname);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    void addList(String list){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, list);

        db.insert(TABLE_NAME_LIST, null, values);

        db.close();
    }

    void deleteTodo(Todo delToDo, String listname){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ITEM + " = ? AND " + COLUMN_LISTNAME + " = ?", new String[] {delToDo.todo, listname});
        db.close();
    }

    void deleteList(String list){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_LIST, COLUMN_ITEM + " = ?", new String[] {list});
        db.delete(TABLE_NAME, COLUMN_LISTNAME + " = ?", new String[] {list});
        db.close();
    }

    void updateToDo(Todo upToDo, String listname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, upToDo.todo);
        values.put(COLUMN_STATUS, upToDo.status);

        db.update(TABLE_NAME, values, COLUMN_ITEM + " = ? AND " + COLUMN_LISTNAME + " = ?", new String[] {upToDo.todo, listname});

        db.close();
    }

}
