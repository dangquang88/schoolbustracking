package com.school.schoolbustracking.View.FragMent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.school.schoolbustracking.Adapter.ManageBusAdapter;
import com.school.schoolbustracking.Adapter.SetBusAdapter;
import com.school.schoolbustracking.Models.BusListModel;
import com.school.schoolbustracking.Models.MapModel;
import com.school.schoolbustracking.Presenter.BusListView;
import com.school.schoolbustracking.Presenter.MapPresenter;
import com.school.schoolbustracking.Presenter.MapView;
import com.school.schoolbustracking.R;

import java.util.ArrayList;

public class Fragment_manageBus extends Fragment implements MapView {
    private MapPresenter mapPresenter;
    private ManageBusAdapter busList;

    //components
    private ListView busListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_bus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get bus data from firebase
        mapPresenter = new MapPresenter(this);
        mapPresenter.HandleReadData();

        //set up all buses list view
        busListView = view.findViewById(R.id.manageBusListView);
        busList = new ManageBusAdapter(getActivity(), new ArrayList<>());
        busListView.setAdapter(busList);
    }

    @Override
    public void getDataMap(double lat, double lo, String address){
        busList.add(new MapModel(lat, lo, address));
    }
}
