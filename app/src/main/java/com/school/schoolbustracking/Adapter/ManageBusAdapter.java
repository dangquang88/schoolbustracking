package com.school.schoolbustracking.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.MapModel;
import com.school.schoolbustracking.Presenter.BusListPresenter;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.FragMent.Fragment_checkInBusList;

import java.util.ArrayList;

public class ManageBusAdapter extends ArrayAdapter<MapModel> {
    public ManageBusAdapter(Context context, ArrayList<MapModel> busList){
        super(context, 0, busList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the bus with the correct position
        MapModel bus = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.set_bus_list_item, parent, false);
        }

        // Lookup view for data population
        TextView indexField = (TextView) convertView.findViewById(R.id.setBusItemIndex);
        TextView addressField = (TextView) convertView.findViewById(R.id.setBusItemAddress);

        // Populate the data into the template view using the data object
        indexField.setText("bus " + (position + 1) + ": ");
        addressField.setText(bus.getAddress());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("address", bus.getAddress());

                Fragment_checkInBusList fragment = new Fragment_checkInBusList();
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.framelayout, fragment);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
