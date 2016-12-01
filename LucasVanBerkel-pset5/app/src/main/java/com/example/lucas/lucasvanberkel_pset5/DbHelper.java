package com.example.lucas.lucasvanberkel_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDo";
    private static final String TABLE_NAME = "Table_ToDo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM = "item";
    private static final String COLUMN_STATUS = "status";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_ITEM + " TEXT," + COLUMN_STATUS + " INTERGER" + " )";


    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        String[] standard= {"Laundry", "Dishes", "Homework"};
        ContentValues values = new ContentValues();
        for(String item:standard) {
            values.put(COLUMN_ITEM, item);
            values.put(COLUMN_STATUS, 0);
            db.insert(TABLE_NAME, null, values);
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    ArrayList<Todo> getAllToDo(){
        ArrayList<Todo> list = new ArrayList<>();
        String query = "Select * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                list.add(new Todo(c.getString(c.getColumnIndex(COLUMN_ITEM)),
                        c.getInt(c.getColumnIndex(COLUMN_STATUS))));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    void addTodo(Todo newToDo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, newToDo.todo);
        values.put(COLUMN_STATUS, newToDo.status);

        db.insert(TABLE_NAME, null, values);
    }

    void deleteTodo(Todo delToDo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ITEM + " = ?", new String[] {delToDo.todo});
    }

    void updateToDo(Todo upToDo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, upToDo.todo);
        values.put(COLUMN_STATUS, upToDo.status);

        db.update(TABLE_NAME, values, COLUMN_ITEM + " = ?", new String[] {upToDo.todo});
    }

}
