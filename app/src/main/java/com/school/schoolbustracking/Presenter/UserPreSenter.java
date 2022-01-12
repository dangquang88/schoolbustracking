package com.school.schoolbustracking.Presenter;

import com.school.schoolbustracking.Models.TeacherModel;
import com.school.schoolbustracking.Models.UserModels;

public class UserPreSenter implements  IUSERMODEL {

    private UserModels userModels;
    private UserView callback;
    private TeacherModel teacherModel;

    public UserPreSenter(UserView callback) {
        this.callback = callback;
        userModels = new UserModels(this);
        teacherModel = new TeacherModel(this);
    }

    public void HandleLoginUser(String username, String pass) {
        userModels.HandleLoginUser(username, pass);
    }
    public  void HandleDataTeacher(String uid){
        teacherModel.HandlegetProFileUser(uid);
    }
    public  void HandleContactTeacher(String uid){
        teacherModel.HandlegetContactTeacher(uid);
    }

    @Override
    public void OnSucess(String uid) {
        callback.OnSucess(uid);
    }

    @Override
    public void OnFail(String s) {
        callback.OnFail(s);

    }

    @Override
    public void getDataProfile(String uid, String address, String gender, String date_of_birth, String fullname, String grade, double lat, double lo, String tokenfcm,
                               String photo,String uid_teacher,String phonenumber) {
        callback.getDataProfile(uid,address,gender,date_of_birth,fullname,grade,lat,lo,tokenfcm,photo,uid_teacher,phonenumber);
    }

    @Override
    public void getDataProfileTeacher(String uid, String address, String gender, String date_of_birth, String fullname,
                                      String grade, double lat, double lo, String tokenfcm, String photo,String phonenumber) {
        callback.getDataproFileTeacher(uid,address,gender,date_of_birth,fullname,grade,lat,lo,tokenfcm,photo,phonenumber);
    }

    public void HandleGetProFile(String uid) {
        userModels.HandlegetProFileUser(uid);
    }

    public void HadlegetDataStudent(String uid) {
        teacherModel.HandleGetProfileStudents(uid);
    }

    public void HandleLoginUserTeacher(String username, String pass) {
        teacherModel.HandleLoginUser(username,pass);
    }

    public void HandleGetProFileTeacher(String uid) {
        teacherModel.HandlegetProFileUser(uid);
    }

    public void HandleUpdateToken(String uid, int i,String token) {
        userModels.HandleUpdateToken(uid,i,token);
    }

    public void HandleUpdateCall(String uid, int i, String active) {
        userModels.HandleUpdateCall(uid,i,active);
    }

    public void HandleUpdatePhoto(String toString,int i,String uid) {
        userModels.HandleUpdatePhoto(toString, i,uid);
    }
}


