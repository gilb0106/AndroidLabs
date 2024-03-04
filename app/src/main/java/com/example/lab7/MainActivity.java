package com.example.lab7;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<Character> characters;
    private static boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        isTablet = findViewById(R.id.fragmentLocation) != null;
        ListView listView = findViewById(R.id.theListView);
        new FetchCharactersTask(this, listView).execute();
    }

    private static class FetchCharactersTask extends AsyncTask<Void, Void, ArrayList<Character>> {
        private Context mContext;
        private ListView mListView;

        public FetchCharactersTask(Context context, ListView listView) {
            mContext = context;
            mListView = listView;
        }

        @Override
        protected ArrayList<Character> doInBackground(Void... voids) {
            ArrayList<Character> characters = new ArrayList<>();
            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");  // delimiter for start of each character
                    if (scanner.hasNext()) {
                        String response = scanner.next();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultsArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < resultsArray.length(); i++) {
                            JSONObject characterObject = resultsArray.getJSONObject(i);
                            String name = characterObject.getString("name");
                            String height = characterObject.getString("height");
                            String mass = characterObject.getString("mass");
                            Character character = new Character(name, height, mass);
                            characters.add(character);
                        }
                    }   //  build characteer object to store in array results
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }  // used finally so we always close connection
            } catch (IOException e) {
                e.printStackTrace();
            }
            return characters; // return characters
        }

        @Override
        protected void onPostExecute(ArrayList<Character> characters) {
            super.onPostExecute(characters);
            MainActivity.characters = characters;
            ListAdapter adapter = new ListAdapter(mContext, characters);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener((parent, view, position, id) -> {
                Character selectedCharacter = MainActivity.characters.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("name", selectedCharacter.getName());
                bundle.putString("height", selectedCharacter.getHeight());
                bundle.putString("mass", selectedCharacter.getMass());

                if (isTablet) {
                    DetailsFragment dFragment = new DetailsFragment();
                    dFragment.setArguments(bundle);
                    ((AppCompatActivity) mContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentLocation, dFragment) // Add the fragment in FrameLayout
                            .commit(); // Actually load the fragment.
                } else {
                    Intent intent = new Intent(mContext, EmptyActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }}