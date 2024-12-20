package com.example.moked_report;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioGroupManager;
    EditText editTextName;
    EditText passwordEditText;
    SharedPreferences sharedPreferences;
    String workerPassword = "1234";
    String managerPassword = "4321";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        //first enter check
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();  // Use apply() or commit() to save changes

        String userName = sharedPreferences.getString("userName", null);
        if (userName != null) {
            // User already signed in, go to right activity
            String userRole = sharedPreferences.getString("userRole", null);
            assert userRole != null;
            if (userRole.equals("manager"))
                startActivity(new Intent(this, manager.class));
            else
                startActivity(new Intent(this, worker.class));
        } else {
            // User needs to sign in
            setContentView(R.layout.activity_main);
            radioGroupManager = (RadioButton) findViewById(R.id.managerCheck);
            Button signInButton = findViewById(R.id.sign_in_button);
            editTextName = (EditText) findViewById(R.id.editTextName);
            passwordEditText = findViewById(R.id.password_toggle);
            TextView passwordText = findViewById(R.id.PasswordText);
            radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


            //radioGroup listener
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    signInButton.setBackgroundResource(R.drawable.rounded_corner);
                    passwordText.setVisibility(View.VISIBLE);
                    passwordEditText.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    public void onClickLogIn(View v) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(MainActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {

            String name = editTextName.getText().toString().trim();
            if (!name.isEmpty()) {

                this.saveNameToSharedPreference(name);
                String password = passwordEditText.getText().toString().trim();

                if (selectedId == (R.id.managerCheck)) {
                    if(password.equals(managerPassword)){
                        this.saveRoleToSharedPreference("manager");
                        this.addUserToFireStore(name,"manager");
                        startActivity(new Intent(this, manager.class));
                    } else{
                        Toast.makeText(MainActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(password.equals(workerPassword)){
                        this.saveRoleToSharedPreference("worker");
                        this.addUserToFireStore(name,"worker");
                        startActivity(new Intent(this, worker.class));
                    } else{
                        Toast.makeText(MainActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                // Show error message if name field is empty
                Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void saveNameToSharedPreference(String name) {
        // Save name to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", name);
        editor.apply();
    }

    public void saveRoleToSharedPreference(String role) {
        // Save Role to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userRole", role);
        editor.apply();
    }

    public void addUserToFireStore(String name, String role){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

// Add a new document with a generated ID
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("role", role);

        usersRef.add(user)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document", e);
                });
    }
}

