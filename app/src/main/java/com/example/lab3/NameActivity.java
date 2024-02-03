package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Intent dataSent = getIntent();
        String nameSent = dataSent.getStringExtra("name");
        TextView textView = findViewById(R.id.textView);
        textView.setText(nameSent);
    }
}