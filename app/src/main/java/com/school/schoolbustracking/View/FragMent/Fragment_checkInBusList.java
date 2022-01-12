package com.school.schoolbustracking.View.FragMent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.school.schoolbustracking.Adapter.CheckInBusAdapter;
import com.school.schoolbustracking.Models.BusListModel;
import com.school.schoolbustracking.Presenter.BusListPresenter;
import com.school.schoolbustracking.Presenter.BusListView;
import com.school.schoolbustracking.Presenter.UserView;
import com.school.schoolbustracking.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_checkInBusList extends Fragment implements BusListView {
    private CheckInBusAdapter busAdapter;
    private BusListPresenter busListPresenter;

    //bundle
    private Bundle bundle;

    //data
    private String busAddress;
    private ArrayList<HashMap<String, Object>> checkInList;

    //components
    private ListView checkInListView;
    private Button returnBtn;
    private TextView addressField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_in_bus_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get data from bundle
        bundle = this.getArguments();
        if(bundle != null){
            busAddress = bundle.getString("address");
        }

        //set up address field
        addressField = view.findViewById(R.id.checkInBusAddressField);
        addressField.setText("Bus address: " + busAddress);

        //set up return button
        returnBtn = view.findViewById(R.id.checkInBusReturnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //get the student list
        busListPresenter = new BusListPresenter(this);
        busListPresenter.handleGetStudentListWithBusAddress(busAddress);

        //set up check in list view
        checkInList = new ArrayList<>();
        checkInListView = view.findViewById(R.id.checkInBusListView);
        busAdapter = new CheckInBusAdapter(getActivity(), checkInList, busListPresenter);
        checkInListView.setAdapter(busAdapter);
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataBusListItem(BusListModel model) {
        return;
    }

    @Override
    public void getDataUserCheckInListItem(HashMap<String, Object> item) {
        busAdapter.add(item);
    }

    @Override
    public void updateStatusCheckInListItem(int itemIndex, boolean value) {
        HashMap currentRegister = checkInList.get(itemIndex);
        currentRegister.put("onBoard", value);
        busAdapter.notifyDataSetChanged();
    }
}
