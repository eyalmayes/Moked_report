package com.example.moked_report;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class manager extends AppCompatActivity  {

    TextView nameText;
    String userName;
    SharedPreferences sharedPreferences;
    private static final int SQUARE_COUNT = 20;
    private SquareView[] squareViews;

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

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        squareViews = new SquareView[SQUARE_COUNT];

        for (int i = 0; i < SQUARE_COUNT; i++) {
            squareViews[i] = new SquareView(this);
            final int index = i; // Needed for the click listener
            squareViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSquareClick(index);
                }
            });
            gridLayout.addView(squareViews[i]);
        }
    }

    private void onSquareClick(int index) {
        // Handle square click
        // Example: Change the color of the clicked square
        squareViews[index].setBackgroundColor(Color.RED);
    }

    // Custom view for square
    private class SquareView extends View {
        public SquareView(manager context) {
            super(context);
            setLayoutParams(new LinearLayout.LayoutParams(250, 250)); // Set square dimensions
            setBackgroundColor(Color.BLUE); // Initial color
            setPadding(8, 8, 8, 8); // Add some padding
        }
    }

}