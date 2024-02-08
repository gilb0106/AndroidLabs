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
    private  int BUTTON_RESULT = 1;
    SharedPreferences prefs = null; // instantiate sp obj to null
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);  // create sp file, FileName
        String savedString = prefs.getString("ReserveName", ""); // if ReserveName in file has entry disp,if not leave blank
        editText = findViewById(R.id.editText);
        editText.setText(savedString);  // setText for edit text to savedString value, entry from FileName
        Button nextButton = findViewById(R.id.button);

        Intent nextPage = new Intent(MainActivity.this, NameActivity.class);
        nextButton.setOnClickListener(click -> {
            nextPage.putExtra("name", editText.getText().toString());  //listener with Intent obj to forward inputted name to nameactivity

            startActivityForResult(nextPage, BUTTON_RESULT);  /*
            For result to expect return to either close or stay on this page */

        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Main Activity", "In onPause()");
        saveSharedPrefs(editText.getText().toString());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // method to handle btn choice on nameactivity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BUTTON_RESULT) {
            if (resultCode == 0) {// if 0 do nothing, just stay on page
            } else if (resultCode == 1)  // shut er down
                finish();
        }
    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit(); // call editor to save input name
        editor.putString("ReserveName", stringToSave);
        editor.commit(); // saving inputed name to sp FileName
    }
}