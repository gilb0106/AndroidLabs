package com.example.lab7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {
    boolean isTablet;
    private Character character; // made character class for nice easy storage and variable passing
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            character = new Character(
                    bundle.getString("name"),
                    bundle.getString("height"),
                    bundle.getString("mass")
            );
            TextView nameTextView = result.findViewById(R.id.textView1b);
            TextView heightTextView = result.findViewById(R.id.textView2b);
            TextView massTextView = result.findViewById(R.id.textView3b);
            nameTextView.setText(character.getName());
            heightTextView.setText(character.getHeight());
            massTextView.setText(character.getMass());
        }
        setHasOptionsMenu(true);
        isTablet = result.findViewById(R.id.test) != null;
        //created 720p fragment_layout with test id so i could customize action bar
        return result;
    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null && !isTablet) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } // if not tablet  provide arrow
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
        // logic for back button if not tablet  (home)
    }
}
