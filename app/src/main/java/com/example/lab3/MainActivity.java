package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent nextPage = new Intent(this, NameActivity.class);
        Button secondButton = findViewById(R.id.button);
        secondButton.setOnClickListener( click -> startActivity( nextPage ));

    }
}