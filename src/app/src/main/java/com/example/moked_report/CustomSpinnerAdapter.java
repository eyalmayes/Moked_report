package com.example.moked_report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private Context context;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, List<String> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_spinner_item, null);
        }

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(items.get(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_item, null);
        }

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(items.get(position));
        return view;
    }
}

