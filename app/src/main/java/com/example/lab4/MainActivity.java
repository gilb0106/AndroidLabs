package com.example.lab4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = findViewById(R.id.addButton);
        editText = findViewById(R.id.editText);

        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter(myAdapter = new MyListAdapter());

        addButton.setOnClickListener(click -> {
            String userInput = editText.getText().toString().trim();
            if (!userInput.isEmpty()) {
                boolean isUrgent = ((Switch) findViewById(R.id.switchBox)).isChecked();
                TodoItem item = new TodoItem(userInput, isUrgent);
                elements.add(item);
                myAdapter.notifyDataSetChanged();
                editText.getText().clear();
            }
        });

        myList.setOnItemClickListener((parent, view, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setMessage("Selected row is: " + (pos))
                    .setPositiveButton("Yes", (click, arg) -> {
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })

                    //Show the dialog
                    .create().show();

        });
        Switch urgentSwitch = findViewById(R.id.switchBox);
        urgentSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int position = myList.getPositionForView(buttonView);
            if (position != ListView.INVALID_POSITION) {
                elements.get(position).setUrgent(isChecked);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }

        public Object getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.row_layout, parent, false);
            TextView tView = newView.findViewById(R.id.textGoesHere);
            TodoItem currentItem = elements.get(position);
            tView.setText(currentItem.getText());

            if (currentItem.isUrgent()) {
                newView.setBackgroundColor(Color.RED);
                tView.setTextColor(Color.WHITE);
            } else {

                newView.setBackgroundColor(Color.TRANSPARENT);
                tView.setTextColor(Color.BLACK);
            }

            return newView;
        }
    }
    private class TodoItem { // requirement 5
        private String text;
        private boolean urgent;
        public TodoItem(String text, boolean urgent) {
            this.text = text;
            this.urgent = urgent;
        }
        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        public boolean isUrgent() {
            return urgent;
        }
        public void setUrgent(boolean urgent) {
            this.urgent = urgent;
        }
    }
}