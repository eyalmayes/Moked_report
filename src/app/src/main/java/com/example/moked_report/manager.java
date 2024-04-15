package com.example.moked_report;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class manager extends AppCompatActivity  {

    TextView nameText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        nameText = (TextView)findViewById(R.id.nameTextM);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nameText.setText(extras.getString("name"));
        }
    }

    @Override
    protected void onPause() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", (String) nameText.getText()).commit();
        editor.putString("job", "manager");

//        editor.remove("job");
        editor.apply();



//        SharedPreferences.Editor editor = sp.edit();
//        editor.remove("job");
//        editor.apply();

        super.onPause();
    }
}