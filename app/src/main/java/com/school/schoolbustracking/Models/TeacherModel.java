package com.school.schoolbustracking.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.school.schoolbustracking.Presenter.IUSERMODEL;

import java.io.Serializable;

public class TeacherModel implements Serializable {
    private  String username;
    private  String password;
    private  String tokenfcm;
    private  String gender;
    private  String date_of_birth;
    private  String address;
    private  String fullname;
    private  String grade;
    private  String uid;
    private  String photo;
    private  String phonenumber;
    private  double lat;
    private  double lo;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private IUSERMODEL callback;

    public TeacherModel(){

    }
    public TeacherModel(IUSERMODEL callback){
        this.callback=callback;
        firebaseDatabase = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
        databaseReference =firebaseDatabase.getReference();


    }

    public TeacherModel(String username, String password, String tokenfcm, String gender, String date_of_birth, String address, String fullname,
                        String grade, String uid, double lat, double lo, String photo,String phonenumber) {
        this.username = username;
        this.password = password;
        this.tokenfcm = tokenfcm;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.address = address;
        this.fullname = fullname;
        this.grade = grade;
        this.uid = uid;
        this.lat = lat;
        this.lo = lo;
        this.photo=photo;
        this.phonenumber =phonenumber;

    }
    public TeacherModel( String tokenfcm, String gender, String date_of_birth, String address, String fullname,
                        String grade, String uid, double lat, double lo, String photo,
                         String phonenumber) {

        this.tokenfcm = tokenfcm;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.address = address;
        this.fullname = fullname;
        this.grade = grade;
        this.uid = uid;
        this.lat = lat;
        this.lo = lo;
        this.photo=photo;
        this.phonenumber = phonenumber;

    }


    public String getPhoto() {
        return photo;
    }

    public String getGender() {
        return gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public String getFullname() {
        return fullname;
    }

    public String getGrade() {
        return grade;
    }
    public  void HandlegetProFileUser(String uid){
        databaseReference.child("Teacher").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeacherModel userModels = snapshot.getValue(TeacherModel.class);

                callback.getDataProfileTeacher(userModels.getUid(), userModels.getAddress(),userModels.getGender(),
                        userModels.getDate_of_birth(),userModels.getFullname(),userModels.getGrade(),
                        userModels.getLat(),userModels.getLo(),userModels.getTokenfcm(),userModels.getPhoto(),
                        userModels.getPhonenumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public  void HandlegetContactTeacher(String uid){
        databaseReference.child("Teacher").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeacherModel userModels = snapshot.getValue(TeacherModel.class);
                callback.getDataProfileTeacher(userModels.getUid(), userModels.getAddress(),userModels.getGender(),
                        userModels.getDate_of_birth(),userModels.getFullname(),userModels.getGrade(),
                        userModels.getLat(),userModels.getLo(),userModels.getTokenfcm(),userModels.getPhoto(),
                        userModels.getPhonenumber());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void HandleLoginUser(String username, String pass){

        databaseReference.child("Teacher")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot!=null){
                   for(DataSnapshot d : snapshot.getChildren()){
                       TeacherModel userModels = d.getValue(TeacherModel.class);
                       if(userModels.getUsername().equalsIgnoreCase(username) && userModels.getPassword().equals(pass)){
                           callback.OnSucess(userModels.getUid());
                       }else{
                          callback.OnFail("Wrong account / Wrong Password");
                       }
                   }

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTokenfcm() {
        return tokenfcm;
    }

    public String getUid() {
        return uid;
    }

    public double getLo() {
        return lo;
    }

    public double getLat() {
        return lat;
    }

    public void HandleGetProfileStudents(String uid) {
        databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()){
                    UserModels userModels = d.getValue(UserModels.class);

                if(userModels.getUid_teacher().equalsIgnoreCase(uid)){
                    callback.getDataProfile(userModels.getUid(), userModels.getAddress(),userModels.getGender(),
                            userModels.getDate_of_birth(),userModels.getFullname(),userModels.getGrade(),
                            userModels.getLat(),userModels.getLo(),userModels.getTokenfcm(),
                            userModels.getPhoto(),userModels.getUid_teacher(),userModels.getPhonenumber());
                }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
