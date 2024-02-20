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
    TodoDAO todoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        Button addButton = findViewById(R.id.addButton);
        ListView listView = findViewById(R.id.theListView);

        todoDAO = new TodoDAO(this);
        todoDAO.printCursor(todoDAO.getAllItemsCursor());
        todoItems = todoDAO.getAllItems();
        myAdapter = new MyListAdapter();
        listView.setAdapter(myAdapter);

        addButton.setOnClickListener(view -> {
            String userInput = editText.getText().toString().trim();
            if (!userInput.isEmpty()) {
                boolean isUrgent = ((Switch) findViewById(R.id.switchBox)).isChecked();
                TodoItem item = new TodoItem(userInput, isUrgent);
                long id = todoDAO.addItem(item);
                item.setId(id);
                todoItems.add(item);
                myAdapter.notifyDataSetChanged();
                editText.getText().clear();
                Toast.makeText(this, getString(R.string.added) + id, Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> showTodoItem(position));
    }

    private void showTodoItem(int pos) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.delete_prompt))
                .setMessage(getString(R.string.selected_row) + " " + (pos))
                .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                    TodoItem item = todoItems.get(pos);
                    todoItems.remove(pos);
                    todoDAO.deleteItem(item.getId());
                    myAdapter.notifyDataSetChanged();
                })
                .setNegativeButton(getString(R.string.no), (click, arg) -> {
                })

                .create().show();
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
        public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
            android.view.View newView = getLayoutInflater().inflate(R.layout.row_layout, parent,
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
}