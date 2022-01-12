package com.school.schoolbustracking.Models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.school.schoolbustracking.Presenter.IMap;

import java.io.Serializable;

public class MapModel implements Serializable {

    private  double lat;
    private  double lo;
    private  String address;
    private  IMap callback;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public MapModel(double lat, double lo, String address) {
        this.lat = lat;
        this.lo = lo;
        this.address = address;
    }
    public  MapModel(){

    }
    public  MapModel(IMap callback){
        this.callback=callback;
        firebaseDatabase=FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();

    }
    public  void HandleReadDataMap(){
        databaseReference.child("Bus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){


                    for(DataSnapshot m  : snapshot.getChildren()){
                        MapModel map = m.getValue(MapModel.class);
                        callback.getDataMap(map.getLat(),map.getLo(),map.getAddress());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public double getLat() {
        return lat;
    }

    public double getLo() {
        return lo;
    }

    public String getAddress() {
        return address;
    }

    public void HandleUpdateLocation(double latitude, double longitude, int k,String uid) {
        switch (k){
            case 1: databaseReference.child("User").child(uid).child("lat").setValue(latitude);
                databaseReference.child("User").child(uid).child("lo").setValue(longitude);break;
            case 2: databaseReference.child("Teacher").child(uid).child("lat").setValue(latitude);
                databaseReference.child("Teacher").child(uid).child("lo").setValue(longitude);break;

        }
    }

    @Override
    public String toString() {
        return "address: " + getAddress();
    }
}
