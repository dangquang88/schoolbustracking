package com.school.schoolbustracking.FCM;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public  static Retrofit retrofit= null;
    public  static  Retrofit getCilent(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl("https://fcm.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return  retrofit;
    }
}
