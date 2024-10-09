package com.example.moked_report;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class manager extends AppCompatActivity  {

    TextView nameText;
    String userName;
    SharedPreferences sharedPreferences;
    private static final int SQUARE_COUNT = 20;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        nameText = (TextView)findViewById(R.id.nameTextM);

        //screen setting
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);
        nameText.setText(userName);

        GridView gridView = findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(this);
        gridView.setAdapter(adapter);

        // Variable to store the position of the clicked item
        final int[] selectedPosition = {-1};  // -1 means no item is selected initially

// Set an OnItemClickListener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Store the clicked position
                selectedPosition[0] = position;   // get the machine number -1

                // Notify the adapter to refresh the view
                adapter.setSelectedPosition(selectedPosition[0]); // Pass the selected position to the adapter
                adapter.notifyDataSetChanged();
            }
        });
    }
}