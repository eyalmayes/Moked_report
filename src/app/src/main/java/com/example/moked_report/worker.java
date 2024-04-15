package com.example.moked_report;

import static com.example.moked_report.Machine.machines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class worker extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView nameText;
    TextView chooseMachineName;
    ImageView chooseMachineImage;
    TextView problemText;
    Machine courentMachine;
    int currentMachineNumber;
    Spinner spinnerProblems;
    int spinnerBrakeAutomaticCall = 0;
    Button buttonStop;
    Button buttonStart;
    TextView endText;

    private static final String ID = "ID";
    private static final String WORKER_NAME = "worker name";
    private static final String MACHINE_NAME = "machin name";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String REASON = "reason";
    private static int reportNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        
        //get worker name from shared pref
        nameText = (TextView) findViewById(R.id.nameText);
        Bundle extras = getIntent().getExtras();
        nameText.setText(extras.getString("name"));
        currentMachineNumber = extras.getInt("currentMachineNumber");

        chooseMachineName = findViewById(R.id.chooseMachineName);
        chooseMachineImage = findViewById(R.id.chooseMachineImage);
        spinnerProblems = findViewById(R.id.spinnerProblems);
        problemText = findViewById(R.id.problemText);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        endText = findViewById(R.id.endText);

        endText.setVisibility(View.INVISIBLE);
        fillMachineDetails(currentMachineNumber);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);


        String[] problems = new String[]{"סיום עבודה", "תקלה", "חלק מביקורת", "הפסקה", "חסר כלי", "צריך עזרת תכנת", "חסר חומר גלם"};
        ArrayAdapter<String> adapterProblems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, problems);
        spinnerProblems.setAdapter(adapterProblems);
        spinnerProblems.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        if(spinnerBrakeAutomaticCall++ > 1){
            if (((String) adapterView.getItemAtPosition(pos)).length() < 3) {
                //מטפל בלחיצה על בחירת המכונה לעבודה
                currentMachineNumber = (Integer.parseInt((String) adapterView.getItemAtPosition(pos))) - 1;
                fillMachineDetails(currentMachineNumber);
            } else {
                // מטפל בלחיצה על סיבת המשתמש להפסקת פעילות המכונה
                courentMachine.setyNotInWork((String) adapterView.getItemAtPosition(pos));
                fillMachineDetails(currentMachineNumber);
                endText.setVisibility(View.VISIBLE);
                endText.setText("תודה על הדיווח, אפשר לסגור את האפליקציה");

            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void fillMachineDetails(int currentMachineNumber){
        courentMachine = machines[currentMachineNumber];
        chooseMachineName.setText(courentMachine.getName());
        chooseMachineImage.setImageResource(courentMachine.getImage());
        problemText.setText("תעדכן סיבת עצירה");
        endText.setVisibility(View.INVISIBLE);
        if (courentMachine.isWork) {
            spinnerProblems.setVisibility(View.INVISIBLE);
            problemText.setVisibility(View.INVISIBLE);
            chooseMachineName.setTextColor(Color.parseColor("#FF00FF19"));
            endText.setVisibility(View.VISIBLE);
        } else {
            problemText.setText(courentMachine.getyNotInWork());
            spinnerProblems.setVisibility(View.VISIBLE);
            problemText.setVisibility(View.VISIBLE);
            chooseMachineName.setTextColor(Color.parseColor("#FF0000"));
        }
    }


    public void onClickStart(View view) {

        courentMachine.setWork(true);
        fillMachineDetails(currentMachineNumber);
        endText.setText("      מעולה, אפשר לסגור את האפליקציה");

    }

    public void onClickStop(View view) {
        courentMachine.setWork(false);
        fillMachineDetails(currentMachineNumber);
        problemText.setText("תעדכן סיבת עצירה");
    }

    @Override
    protected void onPause() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", (String) nameText.getText()).commit();
        editor.putString("job", "worker");
        editor.putInt("currentMachineNumber", currentMachineNumber);

//        editor.remove("job");
        editor.apply();


        super.onPause();
    }

    public static String getCurrentDate(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String currentTime = dateFormat.format(date);
        return currentTime;
    }








    

//קבלת מידע מהdatabase

//       Cursor cursor = dbHelper.getAllData();
//    if (cursor != null && cursor.moveToFirst()) {
//        do {
//            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
//            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
//            int age = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_AGE));
//            // Do something with the data
//        } while (cursor.moveToNext());
//        cursor.close();
//    }



}