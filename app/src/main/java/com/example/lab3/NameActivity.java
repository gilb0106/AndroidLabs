package com.example.lab3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Intent dataSent = getIntent();
        String inputText = dataSent.getStringExtra("name");
        TextView textView = findViewById(R.id.textView);
        textView.setText(getString(R.string.welcome) + " " + inputText);
        Button DCMT = findViewById(R.id.DCMT);
        Button TY = findViewById(R.id.TY);

        DCMT.setOnClickListener(click -> {
            setResult(0);//dont call me that, previous page
            finish();
        });
        
        TY.setOnClickListener(click -> {
            setResult(1); //Thank you button, close app
            finish();
        });
    }
}