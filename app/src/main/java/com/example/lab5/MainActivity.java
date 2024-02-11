package com.example.lab5;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ArrayList<TodoItem> todoItems = new ArrayList<>();
    MyListAdapter myAdapter;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText editText = findViewById(R.id.editText);
        Button addButton = findViewById(R.id.addButton);
        ListView listView = findViewById(R.id.theListView);


        DBStorage dbOpener = new DBStorage(this);
        db = dbOpener.getWritableDatabase();
        Cursor cursor = db.query(DBStorage.TABLE_TODO, null, null, null, null, null, null);
        printCursor(cursor);
        cursor.close();

        loadFromDatabase();


        myAdapter = new MyListAdapter();
        listView.setAdapter(myAdapter);


        addButton.setOnClickListener(view -> {
            String userInput = editText.getText().toString().trim();
            if (!userInput.isEmpty()) {
                boolean isUrgent = ((Switch) findViewById(R.id.switchBox)).isChecked();
                TodoItem item = new TodoItem(userInput, isUrgent);
                long id = addToDatabase(item);
                item.setId(id);
                todoItems.add(item);
                myAdapter.notifyDataSetChanged();
                editText.getText().clear();
                Toast.makeText(this, "Inserted item id:" + id, Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> showTodoItem(position));
    }

    private void loadFromDatabase() {
        String[] columns = {DBStorage.COLUMN_ID, DBStorage.COLUMN_TEXT, DBStorage.COLUMN_URGENT};
        Cursor cursor = db.query(false, DBStorage.TABLE_TODO, columns, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBStorage.COLUMN_ID));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(DBStorage.COLUMN_TEXT));
                int urgentIndex = cursor.getColumnIndex(DBStorage.COLUMN_URGENT);
                int urgent = (urgentIndex != -1) ? cursor.getInt(urgentIndex) : 0;
            TodoItem item = new TodoItem(text, urgent == 1);
            item.setId(id);
            todoItems.add(item);
        }

        cursor.close();
    }

    private long addToDatabase(TodoItem item) {
        ContentValues values = new ContentValues();
        values.put(DBStorage.COLUMN_TEXT, item.getText());
        values.put(DBStorage.COLUMN_URGENT, item.isUrgent() ? 1 : 0);
        return db.insert(DBStorage.TABLE_TODO, null, values);
    }

    private void showTodoItem(int pos) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.delete_prompt))
                .setMessage(getString(R.string.selected_row) + " " + (pos))
                .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                    TodoItem item = todoItems.get(pos);
                    todoItems.remove(pos);
                    delete(item);
                    myAdapter.notifyDataSetChanged();
                })
                .setNegativeButton(getString(R.string.no), (click, arg) -> {
                })

                .create().show();
    }
    private void delete(TodoItem item) {
        db.delete(DBStorage.TABLE_TODO, DBStorage.COLUMN_ID + "= ?",
                new String[] {String.valueOf(item.getId()) });
    }

    private class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return todoItems.size();
        }
        public Object getItem(int position) {
            return todoItems.get(position);
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View newView = getLayoutInflater().inflate(R.layout.row_layout, parent,
                    false);
            TextView textView = newView.findViewById(R.id.textGoesHere);
            TodoItem currentItem = todoItems.get(position);
            textView.setText(currentItem.getText());
            if (currentItem.isUrgent()) {
                newView.setBackgroundColor(Color.RED);
                textView.setTextColor(Color.WHITE);
            } else {
                newView.setBackgroundColor(Color.TRANSPARENT);
                textView.setTextColor(Color.BLACK);
            }
            return newView;
        }
    }
    private void printCursor(Cursor cursor) {
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
    }}

