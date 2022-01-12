package com.school.schoolbustracking.Presenter;

import com.google.firebase.messaging.RemoteMessage;
import com.school.schoolbustracking.Models.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIServices {
   @Headers(
           {
            "Content-Type:application/json",
            "Authorization:key=AAAAh3wVNxY:APA91bFMqjfvVagx1jSRw9k-vhCp1X3jWfCA8mBqI1vEi3uVqkYq-UN1K5odtG1ERcP_3_UhiFVM65uTNHJ07ywySMMfFo82AA3ulEFFx-GSWjJEM37DzMRtFPTwdz-2IGplVmoy-KNk"
           }
   )
    @POST("fcm/send")
   Call<String> sendRemoteMessage(
           @Body Notification remotebody
           );

}
