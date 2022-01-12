package com.school.schoolbustracking.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.MapModel;
import com.school.schoolbustracking.Presenter.BusListPresenter;
import com.school.schoolbustracking.R;

import java.util.ArrayList;

public class SetBusAdapter extends ArrayAdapter<MapModel> {
    private BusListPresenter presenter;
    private ShareF shareF;

    public SetBusAdapter(Context context, ArrayList<MapModel> busList, BusListPresenter presenter){
        super(context, 0, busList);
        this.presenter = presenter;
        this.shareF = new ShareF(context);
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
                //set up alertdialog for bus register
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Register for bus at \"" + bus.getAddress() + "\"?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Confirm",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                presenter.registerBusSlot(shareF.getUID(), bus.getAddress());
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        // Return the completed view to render on screen
        return convertView;

    }
}
