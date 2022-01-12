package com.school.schoolbustracking.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareF {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public  ShareF(Context context ){
        sharedPreferences  = context.getSharedPreferences("INFO",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public  void PutUID(String uid){
        editor.putString("UID",uid);
        editor.commit();
    }
    public  void PutUIDTC(String uid){
        editor.putString("UIDTC",uid);
        editor.commit();
    }
    public  String getUID(){
        return  sharedPreferences.getString("UID","");
    }
    public  String getUIDTC(){
        return  sharedPreferences.getString("UIDTC","");
    }
    public  void putLa(double lat){
        editor.putFloat("lat", (float) lat);
        editor.commit();
    }
    public  void putLo(double lo){
        editor.putFloat("lo", (float) lo);
        editor.commit();
    }
    public  float getLat(){
        return  sharedPreferences.getFloat("lat",0);
    }
    public  float getLo(){
        return  sharedPreferences.getFloat("lo",0);
    }

    public void putToken(String result) {
        editor.putString("token",result);
        editor.commit();
    }
    public  String getToken(){
        return  sharedPreferences.getString("token","");
    }

    public void putUserType(String type){
        editor.putString("userType", type);
        editor.commit();
    }
    public String getUserType(){
        return sharedPreferences.getString("userType", "error");
    }
}
