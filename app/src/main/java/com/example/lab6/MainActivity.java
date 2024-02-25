package com.example.lab6;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView catImageView;
    private ProgressBar progressBar;
    private CatImages catImagesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImageView = findViewById(R.id.catImageView);
        progressBar = findViewById(R.id.progressBar);
        catImagesTask = new CatImages();
        catImagesTask.execute("https://cataas.com/cat?json=true");
    } //Setting view and calling CatImages method to start parse Json
    private class CatImages extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                while (true) { //Run forever
                    URL url = new URL(args[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream response = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }// store url and read JSON data  until end of json data
                    String result = sb.toString(); // store json  as string

                    JSONObject jsonObject = new JSONObject(result); // store as JSON object
                    Log.i("URL", "JSON DATA " + result); //log message for troubleshooting
                    String id;
                    if (jsonObject.has("_id")) {
                        id = jsonObject.getString("_id");
                    } else {
                        Log.e("Error", "JSON response does not contain the key '_id'");
                        return null;
                    } // check for _id tag, used if to handle gracefully
                    // Construct image URL
                    String imageUrl = "https://cataas.com/cat/" + id; // prepare string to be url
                    File imageFile = new File(getFilesDir(), id + ".jpg"); //store image local
                    if (imageFile.exists()) {
                        Log.i("URL", "FILE EXISTS " + id);
                        // Load the image from the device
                        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        return bitmap;
                    } else {
                    url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Log.i("URL", "URL IS: " + imageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(input);

                    //if exists let BitmapFactory to use local copy, if not create new url with imageUrl
                    // Update progress bar
                    for (int i = 0; i < 100; i++) {
                        progressBar.setProgress(i);
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // Reset progress bar before loading a new image
                        progressBar.setProgress(0);
                    return bitmap;
                }}
            } catch (Exception e) {
                Log.e("Error", "Exception Occurred: " + e.toString());
                return null;
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                // Set bitmap to ImageView
                catImageView.setImageBitmap(bitmap);

            } else {
                Log.e("Error", "Failed to load image.");
            }
            // Re-execute the AsyncTask
            new CatImages().execute("https://cataas.com/cat?json=true");
        }
    }}