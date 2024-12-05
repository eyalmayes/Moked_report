package com.example.moked_report;

import static android.content.ContentValues.TAG;
import static com.example.moked_report.Machine.machines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class manager extends AppCompatActivity  {

    TextView nameText;
    static TextView machineName;
    static TextView machineNumber;
    static TextView workerReported;
    static TextView reportDate;
    static TextView status;
    String userName;
    SharedPreferences sharedPreferences;
    private static final int SQUARE_COUNT = 20;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        nameText = (TextView)findViewById(R.id.nameTextM);
        machineName = findViewById(R.id.machineNameTextView);
        machineNumber = findViewById(R.id.machineNumTextView);
        workerReported = findViewById(R.id.workerNameTextView);
        reportDate = findViewById(R.id.reportDateTextView);
        status = findViewById(R.id.statusTextView);

        //screen setting
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);
        nameText.setText(userName);

        Machine.fillArray();
        Machine.updateMachinesArrayFromDataBase();

        GridView gridView = findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(this);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Variable to store the position of the clicked item
        final int[] selectedPosition = {-1};  // -1 means no item is selected initially

// Set an OnItemClickListener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
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


    public static void fillDetailsOfChosenMachine(int chosenMachineNumber){

        Machine chosenMachine = machines[chosenMachineNumber];

        machineName.setText(chosenMachine.name);
        machineNumber.setText(chosenMachine.number);
        workerReported.setText(chosenMachine.lestWorkerReported);
        reportDate.setText(chosenMachine.lastReportDate);
        if(chosenMachine.yNotInWork.isEmpty() ){
            status.setText("machine is working");
            status.setTextColor(Color.GREEN);
        }
        else{
            status.setText(chosenMachine.yNotInWork);
            status.setTextColor(Color.RED);
        }

    }
}