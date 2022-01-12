package com.school.schoolbustracking.View.FragMent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.school.schoolbustracking.Adapter.PersonalBusAdapter;
import com.school.schoolbustracking.Adapter.SetBusAdapter;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.BusListModel;
import com.school.schoolbustracking.Models.MapModel;
import com.school.schoolbustracking.Presenter.BusListPresenter;
import com.school.schoolbustracking.Presenter.BusListView;
import com.school.schoolbustracking.Presenter.MapPresenter;
import com.school.schoolbustracking.Presenter.MapView;
import com.school.schoolbustracking.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_setBusStudent extends Fragment implements MapView, BusListView {
    private SetBusAdapter busList;
    private MapPresenter mapPresenter;
    private BusListPresenter busListPresenter;
    private Button personalListBtn;

    //components
    private ListView busListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_bus_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get bus data from firebase
        mapPresenter = new MapPresenter(this);
        mapPresenter.HandleReadData();

        //set up buslist methods
        busListPresenter = new BusListPresenter(this);

        //set up to personal list btn
        personalListBtn = view.findViewById(R.id.setBusStudentPersonalBtn);
        personalListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new Fragment_personalBus());
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
            }
        });

        //set up all buses list view
        busListView = view.findViewById(R.id.setBusStudentListView);
        busList = new SetBusAdapter(getActivity(), new ArrayList<>(), busListPresenter);
        busListView.setAdapter(busList);
    }

    @Override
    public void getDataMap(double lat, double lo, String address){
        busList.add(new MapModel(lat, lo, address));
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataBusListItem(BusListModel model) {
        //do nothing
        return;
    }

    @Override
    public void getDataUserCheckInListItem(HashMap<String, Object> item) {
        return;
    }

    @Override
    public void updateStatusCheckInListItem(int itemIndex, boolean value) {
        return;
    }
}


