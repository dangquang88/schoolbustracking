package com.school.schoolbustracking.Presenter;

import com.school.schoolbustracking.Models.BusListModel;

import java.util.ArrayList;
import java.util.HashMap;

public class BusListPresenter{
    private BusListModel busListModel;
    private BusListView callbackView;

    public BusListPresenter(BusListView callbackView){
        this.callbackView = callbackView;
        busListModel = new BusListModel(this);
    }

    public void registerBusSlot(String uid, String address){
        busListModel.registerBusSlot(uid, address);
    }

    public void changeStudentStatus(String studentId, String address, boolean status, int index){
        busListModel.changeStudentStatus(studentId, address, status, index);
    }

    public void getPersonalBusList(String uid){
        busListModel.getRegisteredBusSlots(uid);
    }

    public void getDataPersonalListItem(BusListModel model){
        callbackView.getDataBusListItem(model);
    }

    public void handleGetStudentListWithBusAddress(String address){
        busListModel.handleGetStudentListWithBusUID(address);
    }

    public void getDataUserCheckInListItem(HashMap<String, Object> data){
        callbackView.getDataUserCheckInListItem(data);
    }

    public void updateStatusCheckInListItem(int itemIndex, boolean value){
        callbackView.updateStatusCheckInListItem(itemIndex, value);
    }

    public void onSuccess(String message){
        callbackView.onSuccess(message);
    }

    public void onFail(String message){
        callbackView.onFail(message);
    }
}
