package com.school.schoolbustracking.Presenter;

import com.school.schoolbustracking.Models.BusListModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface BusListView {
    void onSuccess(String message);

    void onFail(String message);

    void getDataBusListItem(BusListModel model);

    void getDataUserCheckInListItem(HashMap<String, Object> item);

    void updateStatusCheckInListItem(int itemIndex, boolean value);
}
