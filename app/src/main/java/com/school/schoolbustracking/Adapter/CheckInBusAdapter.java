package com.school.schoolbustracking.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.school.schoolbustracking.Presenter.BusListPresenter;
import com.school.schoolbustracking.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckInBusAdapter extends ArrayAdapter<HashMap<String, Object>> {
    private BusListPresenter presenter;

    public CheckInBusAdapter(Context context, ArrayList<HashMap<String, Object>> busList, BusListPresenter busListPresenter){
        super(context, 0, busList);
        this.presenter = busListPresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the bus with the correct position
        HashMap<String, Object> registration = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.check_in_bus_list_item, parent, false);
        }

        // Lookup view for data population
        TextView studentNameField = (TextView) convertView.findViewById(R.id.checkInBusStudentName);
        TextView statusField = (TextView) convertView.findViewById(R.id.checkInStatus);
        Button changeStatusBtn = convertView.findViewById(R.id.checkInBtn);

        // Populate the data into the template view using the data object
        String studentName = (String) registration.get("studentName");
        studentNameField.setText(studentName);

        boolean status = (Boolean) registration.get("onBoard");
        if(status) statusField.setText("Currently on board");
        else statusField.setText("Not on the bus");

        String address = (String) registration.get("address");
        String studentId = (String) registration.get("studentId");

        changeStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toggle student status
                presenter.changeStudentStatus(studentId, address, !status, position);
            }
        });


        // Return the completed view to render on screen
        return convertView;

    }
}
