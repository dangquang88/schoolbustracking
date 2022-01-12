package com.school.schoolbustracking.View.FragMent;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.school.schoolbustracking.Adapter.ContactAdapter;
import com.school.schoolbustracking.Adapter.ContactAdapter_Teacher;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.TeacherModel;
import com.school.schoolbustracking.Models.UserModels;
import com.school.schoolbustracking.Presenter.UserPreSenter;
import com.school.schoolbustracking.Presenter.UserView;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.HomeActivity;

import java.util.ArrayList;

public class FragMent_Contact extends Fragment
 implements UserView {
    View view;
    private UserPreSenter userPresenter;
    private ContactAdapter_Teacher contactAdapter;
    private ContactAdapter contactAdapter_students;
    private RecyclerView rcv;
    private ArrayList<TeacherModel> arrayList;
    private ArrayList<UserModels> arrayList_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact,container,false);
        rcv = view.findViewById(R.id.rcv);
        Init();
        return  view;
    }

    private void Init() {
        if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},123);
        }
        userPresenter = new UserPreSenter(this);
        arrayList = new ArrayList<>();
        arrayList_user = new ArrayList<>();

        ShareF shareF = new ShareF(getContext());
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("MENU", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("TASK","").equalsIgnoreCase("Student")){
            userPresenter.HandleContactTeacher(shareF.getUIDTC());
        }else{
            userPresenter.HadlegetDataStudent(shareF.getUID());
        }


    }


    @Override
    public void OnSucess(String uid) {

    }

    @Override
    public void OnFail(String s) {

    }

    @Override
    public void getDataProfile(String uid, String address, String gender, String date_of_birth, String fullname, String grade, double lat,
                               double lo, String tokenfcm, String photo, String uid_teacher,String phonenumber) {
        arrayList_user.add(new UserModels(tokenfcm,gender,date_of_birth,address,fullname,grade,uid,lat,lo,photo,uid_teacher,phonenumber));
        contactAdapter_students = new ContactAdapter(getContext(),arrayList_user);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(contactAdapter_students);
    }

    @Override
    public void getDataproFileTeacher(String uid, String address, String gender, String date_of_birth, String fullname, String grade, double lat, double lo, String tokenfcm, String photo,
                                      String phonenumber) {
        arrayList.add(new TeacherModel(tokenfcm,gender,date_of_birth,address,fullname,grade,uid,lat,lo,photo,phonenumber));
        contactAdapter = new ContactAdapter_Teacher(getContext(),arrayList);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(contactAdapter);
    }
}
