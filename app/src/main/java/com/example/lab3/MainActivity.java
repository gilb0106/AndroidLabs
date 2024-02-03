package com.example.lab3;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.lab3.NameActivity;

public class MainActivity extends AppCompatActivity {
    private static final int BUTTON_RESULT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        Button nextButton = findViewById(R.id.button);


        Intent nextPage = new Intent(MainActivity.this, NameActivity.class);
        nextButton.setOnClickListener(click -> {
            nextPage.putExtra("name", editText.getText().toString());


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
        }
