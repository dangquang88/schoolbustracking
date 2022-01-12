package com.school.schoolbustracking.FCM;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.school.schoolbustracking.View.Community.ReceiverActivity;

public class MessagingService  extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("CHECKED",s);
        super.onNewToken(s);


    }
    private  String token="";
    private  String type="";
    private  String room="";
    private  String name = "";
    private  String photo ="";
    private  String uid ="";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        token = remoteMessage.getData().get("token");
        type = remoteMessage.getData().get("type");
        room = remoteMessage.getData().get("room");
        name = remoteMessage.getData().get("name");
        photo = remoteMessage.getData().get("photo");
        uid = remoteMessage.getData().get("uid");
        Log.d("CHECKED",type);

        FirebaseApp.initializeApp(this);

        if(type.equalsIgnoreCase("Invite")){

            Intent intent = new Intent(this, ReceiverActivity.class);
            intent.putExtra("TOKEN",token);
            intent.putExtra("ROOM",room);
            intent.putExtra("PHOTO",photo);
            intent.putExtra("NAME",name);
            intent.putExtra("UID",uid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(type.equalsIgnoreCase("CANCLE")){
            Intent intent = new Intent("RESPONSIVE");
            intent.putExtra("RESPONSIVE",type);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }else if(type.equalsIgnoreCase("CANCLEOS")){
            Intent intent = new Intent("RESPONSIVE");
            intent.putExtra("RESPONSIVE",type);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
        else if(type.equalsIgnoreCase("Acept")){
            Intent intent = new Intent("RESPONSIVE");
            intent.putExtra("RESPONSIVE",type);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }

    }
}
