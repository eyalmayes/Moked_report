package com.example.moked_report;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import static com.example.moked_report.Machine.machines;
import static com.example.moked_report.manager.fillDetailsOfChosenMachine;

import android.content.Context;
import android.graphics.Color;
import android.telephony.IccOpenLogicalChannelResponse;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private int itemCount = 20; // 20 items
    private int selectedPosition = -1; // No item selected initially

    public GridAdapter(Context context) {
        this.context = context;
    }

    // Set the selected position from the activity
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    @Override
    public int getCount() {
        return itemCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_manager, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        // Set the image resource (if not done in XML)
        imageView.setImageResource(R.drawable.robot_arm);

        // Set the number (position + 1 to start from 1)
        textView.setText(String.valueOf(position + 1));

        if (machines[position].isWork) {
            convertView.setBackgroundColor(Color.GREEN); // Change to the desired color
        } else {
            convertView.setBackgroundColor(Color.RED); // Default background for non-selected items
        }
        // Check if this is the clicked item, and change background color
        if (position == selectedPosition){
            Machine.updateMachinesArrayFromDataBase();
            fillDetailsOfChosenMachine(position);
        }

        return convertView;

    }
}
