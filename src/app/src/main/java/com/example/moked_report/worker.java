package com.example.moked_report;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;
import static com.example.moked_report.Machine.machines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class worker extends AppCompatActivity  {

    SharedPreferences sharedPreferences;
    TextView nameText;
    TextView chooseMachineName;
    ImageView chooseMachineImage;
    TextView problemText;
    Spinner spinnerProblems;
    Spinner spinnerMachines;
    Machine courentMachine;
    int currentMachineNumber;
    int spinnerBrakeAutomaticCall = 0;
    Button buttonStop;
    Button buttonStart;
    TextView endText;
    String userName;

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

        nameText = (TextView) findViewById(R.id.nameText);
        chooseMachineName = findViewById(R.id.chooseMachineName);
        chooseMachineImage = findViewById(R.id.chooseMachineImage);
        problemText = findViewById(R.id.problemText);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        endText = findViewById(R.id.endText);
        spinnerProblems = findViewById(R.id.spinnerProblems);
        spinnerMachines = findViewById(R.id.spinnerMachines);

        //screen setting
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);
        currentMachineNumber = sharedPreferences.getInt("LastMachineNumber", 0);
        nameText.setText(userName);
        endText.setVisibility(View.INVISIBLE);
        spinnerProblems.setVisibility(View.VISIBLE);
        Machine.fillArray();
        fillMachineDetails(currentMachineNumber);
        //Machine.setMachinesToFireStore(); //to copy machiens to fireStore

        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");
        items.add("8");
        items.add("9");
        items.add("10");
        items.add("11");
        items.add("12");
        items.add("13");
        items.add("14");
        items.add("15");
        items.add("16");
        items.add("17");
        items.add("18");
        items.add("19");
        items.add("20");
        //String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        spinnerMachines.setAdapter(adapter);
        //dropdown.setOnItemSelectedListener(this);
        spinnerMachines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerBrakeAutomaticCall++ > 1){
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(), "Selected Item: " + selectedItem, Toast.LENGTH_SHORT).show();
                    currentMachineNumber = position;
                    fillMachineDetails(currentMachineNumber);
                    saveLastMachineToSharedPreference(currentMachineNumber);
                    spinnerProblems.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        //String[] problems = new String[]{"סיום עבודה", "תקלה", "חלק מביקורת", "הפסקה", "חסר כלי", "צריך עזרת תכנת", "חסר חומר גלם"};
        //ArrayAdapter<String> adapterProblems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, problems);
        //spinnerProblems.setAdapter(adapterProblems);
        //spinnerProblems.setOnItemSelectedListener(this);

        List<String> problems = new ArrayList<>();
        problems.add("סיום עבודה");
        problems.add("הפסקה");
        problems.add("תקלה");
        problems.add("חלק מביקורת");
        problems.add("חסר כלי");
        problems.add("חסר חומר גלם");
        problems.add("צריך עזרת תכנת");

        CustomSpinnerAdapter adapterProblems = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, problems);
        spinnerProblems.setAdapter(adapterProblems);
        spinnerProblems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerBrakeAutomaticCall++ > 1){
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    // Do something with the selected item, for example, display it in a Toast
                    courentMachine.setyNotInWork((String) parent.getItemAtPosition(position));
                    fillMachineDetails(currentMachineNumber);
                    endText.setVisibility(View.VISIBLE);
                    endText.setText("תודה על הדיווח, אפשר לסגור את האפליקציה");
                    addReportToFireStore("not working");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // @Override
    // public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

    //     if(spinnerBrakeAutomaticCall++ > 1){
    //         if (((String) adapterView.getItemAtPosition(pos)).length() < 3) {
    //             //מטפל בלחיצה על בחירת המכונה לעבודה
    //             currentMachineNumber = (Integer.parseInt((String) adapterView.getItemAtPosition(pos))) - 1;
    //             fillMachineDetails(currentMachineNumber);
    //             saveLastMachineToSharedPreference(currentMachineNumber);
    //         } else {
    //             // מטפל בלחיצה על סיבת המשתמש להפסקת פעילות המכונה
    //             courentMachine.setyNotInWork((String) adapterView.getItemAtPosition(pos));
    //             fillMachineDetails(currentMachineNumber);
    //             endText.setVisibility(View.VISIBLE);
    //             endText.setText("תודה על הדיווח, אפשר לסגור את האפליקציה");
    //             addReportToFireStore("not working");

    //         }
    //     }
    // }


//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

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
        courentMachine.setyNotInWork("");
        addReportToFireStore("working");
        endText.setText("      מעולה, אפשר לסגור את האפליקציה");
    }

    public void onClickStop(View view) {
        courentMachine.setWork(false);
        fillMachineDetails(currentMachineNumber);
        problemText.setText("תעדכן סיבת עצירה");
        spinnerProblems.setVisibility(View.VISIBLE);
    }



    public static Date getCurrentDate(){
        return new Date();
    }

    public void saveLastMachineToSharedPreference(int currentMachineNumber) {
        // Save Last Machine Number to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("LastMachineNumber", currentMachineNumber);
        editor.apply();
    }

    public void addReportToFireStore(String status){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportsRef = db.collection("reports");
        DocumentReference machineRef = db.collection("machines").document(String.valueOf(++currentMachineNumber));
        machineRef.update("status", status,"problem",courentMachine.yNotInWork)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("DocumentSnapshot successfully updated!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating document: " + e.getMessage());
                });
// Add a new document with a generated ID
        Map<String, Object> report = new HashMap<>();
        report.put("date", worker.getCurrentDate());
        report.put("worker name", userName);
        report.put("machine name", courentMachine.getName());
        report.put("status", status);
        report.put("reason for stop", courentMachine.yNotInWork);

        reportsRef.add(report)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + "report" + reportNum);
                    Toast.makeText(this, "report added", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document" + e.getMessage(), e);
                    Toast.makeText(this, "report didn't add", Toast.LENGTH_SHORT).show();
                });
    }
}