package com.example.lab5;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class TodoDAO {
    private SQLiteDatabase db;
    ArrayList<TodoItem> todoItems = new ArrayList<>();
    public TodoDAO(Context context) {
        DBConnect dbOpener = new DBConnect(context);
        db = dbOpener.getWritableDatabase();
    }

    public ArrayList<TodoItem> getAllItems() {

        String[] columns = {DBConnect.COLUMN_ID, DBConnect.COLUMN_TEXT, DBConnect.COLUMN_URGENT};
        Cursor cursor = db.query(false, DBConnect.TABLE_TODO, columns, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBConnect.COLUMN_ID));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(DBConnect.COLUMN_TEXT));
            int urgentIndex = cursor.getColumnIndex(DBConnect.COLUMN_URGENT);
            int urgent = (urgentIndex != -1) ? cursor.getInt(urgentIndex) : 0;
            TodoItem item = new TodoItem(text, urgent == 1);
            item.setId(id);
            todoItems.add(item);
        }
        cursor.close();
        return todoItems;
    }
    public long addItem(TodoItem item) {
        ContentValues values = new ContentValues();
        values.put(DBConnect.COLUMN_TEXT, item.getText());
        values.put(DBConnect.COLUMN_URGENT, item.isUrgent() ? 1 : 0);
        return db.insert(DBConnect.TABLE_TODO, null, values);
    }
    public void deleteItem(long id) {
        db.delete(DBConnect.TABLE_TODO, DBConnect.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void printCursor(Cursor cursor) {
        int versionNumber = db.getVersion();
        Log.d("PrintCursor", "Database version number: " + versionNumber);
        int columnCount = cursor.getColumnCount();
        Log.d("PrintCursor", "Number of columns in the cursor: " + columnCount);

        StringBuilder columnNames = new StringBuilder("Column names: ");
        for (int i = 0; i < columnCount; i++) {
            columnNames.append(cursor.getColumnName(i)).append(", ");
        }
        Log.d("PrintCursor", columnNames.toString());
        cursor.moveToFirst();
        int resultCount = cursor.getCount();
        Log.d("PrintCursor", "Number of results in the cursor: " + resultCount);
        for (int i = 0; i < resultCount; i++) {
            StringBuilder rowData = new StringBuilder("Row " + i + ": ");
            for (int j = 0; j < columnCount; j++) {
                rowData.append(cursor.getString(j)).append(", ");
            }
            Log.d("PrintCursor", rowData.toString());
            cursor.moveToNext();
        }
    }
    public Cursor getAllItemsCursor() {
        String[] columns = {DBConnect.COLUMN_ID, DBConnect.COLUMN_TEXT, DBConnect.COLUMN_URGENT};
        return db.query(DBConnect.TABLE_TODO, columns, null, null, null, null, null);
    }

}