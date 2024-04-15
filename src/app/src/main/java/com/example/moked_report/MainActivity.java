package com.example.moked_report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    int lastMachineNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroupManager = (RadioButton) findViewById(R.id.managerCheck);
        Button button = findViewById(R.id.button);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setText(emplee.name);
        Machine.fillArray();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button.setBackgroundResource(R.drawable.rounded_corner);
            }
        });
    }

//    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//    {
//        public void onCheckedChanged(RadioGroup group, int checkedId)
//        {
//            // This will get the radiobutton that has changed in its check state
//            RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
//            // This puts the value (true/false) into the variable
//            boolean isChecked = checkedRadioButton.isChecked();
//            // If the radiobutton that has changed in check state is now checked...
//            if (isChecked)
//            {
//                // Changes the textview's text to "Checked: example radiobutton text"
//                tv.setText("Checked:" + checkedRadioButton.getText());
//            }
//        }
//    });


    public void onclickbuttonMethod(View v) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if (selectedId == -1 || editTextName.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            emplee.setName(editTextName.getText().toString());
            if (selectedId == (R.id.managerCheck)) {
                emplee.setJob("manager");
                Intent z = new Intent(this, manager.class);
                z.putExtra("name",emplee.name);
                startActivity(z);
            } else {
                emplee.setJob("worker");
                Intent z = new Intent(this, worker.class);
                z.putExtra("name",emplee.name);
                startActivity(z);
            }
            saveForDB();
        }
    }

    public void saveForDB(){
        //save report in database
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.addUser(emplee.name,emplee.job);
        Log.d("DATABASE", "Name: " + emplee.name + ", job: " + emplee.job);
    }




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

