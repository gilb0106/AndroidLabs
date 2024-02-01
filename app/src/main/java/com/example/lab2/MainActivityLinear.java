package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivityLinear extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        final Button btn = findViewById(R.id.button);
        btn.setText(getString(R.string.button));

        final EditText editText = findViewById(R.id.editText);
        final TextView textView = findViewById(R.id.textView);

        btn.setOnClickListener((click) -> {
            // Get the current text from the EditText
            String currentText = editText.getText().toString();

            // Set the current text into the TextView
            textView.setText(currentText);


            String toastMessage = getResources().getString(R.string.toast_message);
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        });
        CheckBox cb = findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            String state = isChecked ? getString(R.string.stateOn) : getString(R.string.stateOff);
            String snackbarMessage = getString(R.string.snackbarMessage) + state;

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), snackbarMessage, Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Undo action: Set the checkbox back to its original state
                    cb.setChecked(!isChecked);
                }
            });

            snackbar.show();
        });
    }
}