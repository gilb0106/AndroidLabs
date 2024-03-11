package com.example.lab8;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For toolbar:
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu); // For Toolbar
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        int id = item.getItemId();
        if (id == R.id.Choice1) {
            // Handle Exit2 menu item click
            Toast.makeText(this, "Choice1 clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.Choice2) {
            // Handle Exit23 menu item click
            Toast.makeText(this, "Choice2 clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.DadJoke) {
            // Inflate dad_joke.xml layout
            LayoutInflater inflater = LayoutInflater.from(this);
            View dadJokeView = inflater.inflate(R.layout.dad_joke, null);

            // Find the TextView containing the dad joke text
            TextView textViewDadJoke = dadJokeView.findViewById(R.id.textViewDadJoke);

            // Get the dad joke text from the TextView
            String dadJokeText = textViewDadJoke.getText().toString();

            // Set the dad joke text to the TextView in the main activity's layout
            TextView textViewDadJokeInMainActivity = findViewById(R.id.textViewDadJoke);
            textViewDadJokeInMainActivity.setText(dadJokeText);
        } else if (id == R.id.Home) {
            // Clear the dad joke text in the main activity's layout
            TextView textViewDadJokeInMainActivity = findViewById(R.id.textViewDadJoke);
            textViewDadJokeInMainActivity.setText("");
        } else if (id == R.id.Exit) {
            // Close the app
            finish();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}