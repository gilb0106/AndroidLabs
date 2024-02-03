package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        Button nextButton = findViewById(R.id.button);

        Intent nextPage = new Intent(MainActivity.this, NameActivity.class);
        nextButton.setOnClickListener(click -> {
            nextPage.putExtra("name", editText.getText().toString());

        startActivity( nextPage );  });




    }
}