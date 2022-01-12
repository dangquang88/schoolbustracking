package com.school.schoolbustracking.Presenter;

import com.school.schoolbustracking.Models.MapModel;
import com.school.schoolbustracking.Models.TeacherModel;

public class MapPresenter implements  IMap{

    private MapModel mapModel;
    private  MapView callback;


    public  MapPresenter(MapView callback){
        this.callback=callback;
        mapModel = new MapModel(this);

    }
    public  void HandleReadData(){
        mapModel.HandleReadDataMap();
    }



    @Override
    public void getDataMap(double lat, double lo, String address) {
        callback.getDataMap(lat,lo,address);
    }

    public void HandleUpdateLocation(double latitude, double longitude,int k,String uid) {
        mapModel.HandleUpdateLocation(latitude,longitude,k,uid);
    }
}
