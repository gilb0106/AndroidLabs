package com.example.lab7;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Character> mCharacters;
    public ListAdapter(Context context, ArrayList<Character> characters) {
        mContext = context;
        mCharacters = characters;
    }
    @Override
    public int getCount() {
        return mCharacters.size();
    }

    @Override
    public Object getItem(int position) {
        return mCharacters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) { // if view not available for reuse, use simplelist default layout
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } // if view does exist, recycle and  load characters
        Character character = mCharacters.get(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(character.getName());
        return view;
    }
}
