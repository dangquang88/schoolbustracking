package com.school.schoolbustracking.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Presenter.BusListPresenter;
import com.school.schoolbustracking.Presenter.BusListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BusListModel implements Serializable {
    private BusListPresenter callbackObj;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String studentId;
    private String address;
    private boolean onBoard;

    //empty constructor for firebase
    public BusListModel(){

    }

    public BusListModel(String uid, String address){
        this.studentId = uid;
        this.address = address;
        this.onBoard = false;
    }

    public BusListModel(BusListPresenter callbackObj){
        this.callbackObj = callbackObj;
        firebaseDatabase=FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public void getRegisteredBusSlots(String uid){
        Query query1 = databaseReference.child("BusList").orderByChild("studentId").equalTo(uid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data: snapshot.getChildren()){
                        BusListModel model = data.getValue(BusListModel.class);
                        callbackObj.getDataPersonalListItem(model);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbackObj.onFail("Database error, please check your internet");
            }
        });
    }

    public void registerBusSlot(String uid, String currentAddress){
        Query query1 = databaseReference.child("BusList").orderByChild("studentId").equalTo(uid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean canCreate = true;

                //check if the user has already registered to the bus
                if(snapshot.exists()){
                    for(DataSnapshot bus: snapshot.getChildren()){
                        BusListModel busModel = bus.getValue(BusListModel.class);
                        if(busModel.getAddress().equals(currentAddress)){
                            callbackObj.onFail("Already registered to this bus");
                            canCreate = false;
                            break;
                        }
                    }
                }

                if(canCreate) {
                    BusListModel newRegister = new BusListModel(uid, currentAddress);
                    databaseReference.child("BusList").push().setValue(newRegister);
                    callbackObj.onSuccess("Registered to bus");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbackObj.onFail("Database error, please check your internet");
            }
        });
    }

    public void changeStudentStatus(String studentId, String address, boolean status, int index){
        Query query = databaseReference.child("BusList").orderByChild("address").equalTo(address);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot registration: snapshot.getChildren()){
                        BusListModel model = registration.getValue(BusListModel.class);
                        if(model.getStudentId().equals(studentId)){
                            databaseReference.child("BusList").child(registration.getKey()).child("onBoard").setValue(status);
                            callbackObj.updateStatusCheckInListItem(index, status);
                            callbackObj.onSuccess("status changed");
                            break;
                        }
                    }
                } else {
                    callbackObj.onFail("Data does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbackObj.onFail("Database error");
            }
        });
    }

    public void handleGetStudentListWithBusUID(String address){
        Query query = databaseReference.child("BusList").orderByChild("address").equalTo(address);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data: snapshot.getChildren()){
                        BusListModel registration = data.getValue(BusListModel.class);
                        databaseReference.child("User").child(registration.getStudentId())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        UserModels student = snapshot2.getValue(UserModels.class);

                                        HashMap<String, Object> studentFormatted = new HashMap<>();
                                        studentFormatted.put("onBoard", registration.isOnBoard());
                                        studentFormatted.put("address", registration.getAddress());
                                        studentFormatted.put("studentId", registration.getStudentId());
                                        studentFormatted.put("studentName", student.getFullname());

                                        callbackObj.getDataUserCheckInListItem(studentFormatted);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error2) {
                                        callbackObj.onFail("Cannot fetch user data");
                                    }
                                });
                    }
                } else {
                    callbackObj.onFail("No students registered on this bus");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbackObj.onFail("Cannot fetch user data");
            }
        });
    }
}
