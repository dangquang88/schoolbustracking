package com.school.schoolbustracking.Presenter;

public interface UserView {

    void OnSucess(String uid);

    void OnFail(String s);

    void getDataProfile(String uid, String address, String gender, String date_of_birth, String fullname, String grade,
                        double lat, double lo, String tokenfcm, String photo,String uid_teacher,String phonenumber);

    void getDataproFileTeacher(String uid, String address, String gender,
                               String date_of_birth, String fullname, String grade, double lat, double lo,
                               String tokenfcm, String photo,String phonenumber);
}