package com.example.moked_report;

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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton genderradioButton;
    RadioButton radioGroupManager;
    EditText editTextName;
    Emplee emplee = new Emplee();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //first enter check
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", null);
        if (userName != null) {
            // User already signed in, go to right activity
            String userRole = sharedPreferences.getString("userRole", null);
            if(userRole.equals("manager"))
                startActivity(new Intent(this, manager.class));
            else
                startActivity(new Intent(this, worker.class));
        } else {
            // User needs to sign in
            setContentView(R.layout.activity_main);
        }

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroupManager = (RadioButton) findViewById(R.id.managerCheck);
        Button signInButton = findViewById(R.id.sign_in_button);
        editTextName = (EditText) findViewById(R.id.editTextName);

        //radioGroup listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                signInButton.setBackgroundResource(R.drawable.rounded_corner);
            }
        });
    }


    public void onClickLogIn(View v) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(MainActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {

            String name = editTextName.getText().toString().trim();
            if (!name.isEmpty()) {

                this.saveNameToSharedPreference(name);

                if (selectedId == (R.id.managerCheck)) {
                    this.saveRoleToSharedPreference("manager");
                    startActivity(new Intent(this, manager.class));
                } else {
                    this.saveRoleToSharedPreference("worker");
                    startActivity(new Intent(this, worker.class));
                }
            }else{
                // Show error message if name field is empty
                Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void saveNameToSharedPreference(String name){
        // Save name to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", name);
        editor.apply();
    }

    public void saveRoleToSharedPreference(String role){
        // Save Role to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userRole", role);
        editor.apply();
    }



//    public void onclickbuttonMethod(View v) {
//
//        int selectedId = radioGroup.getCheckedRadioButtonId();
//        genderradioButton = (RadioButton) findViewById(selectedId);
//        if (selectedId == -1 || editTextName.getText().toString().isEmpty()) {
//            Toast.makeText(MainActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
//        } else {
//            emplee.setName(editTextName.getText().toString());
//            if (selectedId == (R.id.managerCheck)) {
//                emplee.setJob("manager");
//                Intent z = new Intent(this, manager.class);
//                z.putExtra("name",emplee.name);
//                startActivity(z);
//            } else {
//                emplee.setJob("worker");
//                Intent z = new Intent(this, worker.class);
//                z.putExtra("name",emplee.name);
//                startActivity(z);
//            }
//            saveForDB();
//        }
//    }
//
//    public void saveForDB(){
//        //save report in database
//        MyDatabaseHelper db = new MyDatabaseHelper(this);
//        db.addUser(emplee.name,emplee.job);
//        Log.d("DATABASE", "Name: " + emplee.name + ", job: " + emplee.job);
//    }




    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//
//        lastMachineNumber = sp.getInt("courentMachineNumber", 0);
//        emplee.name = sp.getString("name", "");
//        emplee.job = sp.getString("job", "");
//
//        if((sp.getString("job", "")).equals("worker")){
//            Intent z = new Intent(this, worker.class);
//            z.putExtra("name",emplee.name);
//            z.putExtra("courentMachineNumber",sp.getInt("courentMachineNumber", 0));
//            startActivity(z);
//        }
//        if((sp.getString("job", "")).equals("manager")){
//            Intent z = new Intent(this, manager.class);
//            z.putExtra("name",emplee.name);
//            startActivity(z);
//        }
//    }

