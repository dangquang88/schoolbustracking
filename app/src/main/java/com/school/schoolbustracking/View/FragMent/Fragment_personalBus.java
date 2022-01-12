package com.school.schoolbustracking.View.FragMent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.school.schoolbustracking.Adapter.PersonalBusAdapter;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.BusListModel;
import com.school.schoolbustracking.Presenter.BusListPresenter;
import com.school.schoolbustracking.Presenter.BusListView;
import com.school.schoolbustracking.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_personalBus extends Fragment implements BusListView {
    private PersonalBusAdapter personalBusList;
    private BusListPresenter busListPresenter;
    private ShareF shareF;

    //components
    private ListView personalListView;
    private Button returnBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //get shareF
        shareF = new ShareF(getActivity());

        return inflater.inflate(R.layout.fragment_personal_bus_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set up buslist methods
        busListPresenter = new BusListPresenter(this);
        busListPresenter.getPersonalBusList(shareF.getUID());

        //set up return btn
        returnBtn = view.findViewById(R.id.personalBusReturnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //set up personal bus listview
        personalListView = view.findViewById(R.id.personalBusListView);
        personalBusList = new PersonalBusAdapter(getActivity(), new ArrayList<>());
        personalListView.setAdapter(personalBusList);
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
        personalBusList.add(model);
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
