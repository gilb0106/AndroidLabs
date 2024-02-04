package com.example.lab3;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.example.lab3.NameActivity;

public class MainActivity extends AppCompatActivity {
    private static final int BUTTON_RESULT = 1;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("ReserveName", "");
        EditText editText = findViewById(R.id.editText);
        editText.setText(savedString);
        Button nextButton = findViewById(R.id.button);


        Intent nextPage = new Intent(MainActivity.this, NameActivity.class);
        nextButton.setOnClickListener(click -> {
            saveSharedPrefs(editText.getText().toString());
            nextPage.putExtra("name", editText.getText().toString());
            editText.setText(savedString);

            startActivityForResult(nextPage, BUTTON_RESULT);  /*
            For result to expect return to either close or stay on this page */

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BUTTON_RESULT) {
            if (resultCode == 0) {// if 0 do nothing, just stay on page
            } else if (resultCode == 1)  // shut er down
                finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Main Activity", "In onPause()");

    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", stringToSave);
        editor.commit();
    }
}