package com.school.schoolbustracking.View.Community;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.facebook.react.modules.core.PermissionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.FCM.API;
import com.school.schoolbustracking.Models.Data;
import com.school.schoolbustracking.Models.Notification;
import com.school.schoolbustracking.Presenter.APIServices;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.HomeActivity;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.URL;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoCallActivity  extends AppCompatActivity implements JitsiMeetViewListener, JitsiMeetActivityInterface {
    private Intent intent;
    private JitsiMeetConferenceOptions options1;
    private JitsiMeetView jitsiMeetView;
    private  String Room="";
    private String UID ="";
    private String TOKEN ="";
    private  ShareF shareF;
    private int check_user = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_jitsimeet);

        jitsiMeetView = new JitsiMeetView(this);
        SharedPreferences sharedPreferences= getSharedPreferences("MENU", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("TASK","").equalsIgnoreCase("Student")){
           check_user = 0;
        }else{
            check_user = 1;
        }
        intent=getIntent();
        shareF = new ShareF(this);
        Room = intent.getStringExtra("ROOM");
        UID = intent.getStringExtra("UID");
        TOKEN = intent.getStringExtra("TOKEN");
        try{
            URL url = new URL("https://meet.jit.si/");
            JitsiMeetConferenceOptions options =  new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(url)
                    .setWelcomePageEnabled(true)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(options);
            options1 = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(Room)
                    .setFeatureFlag("call-integration.enabled", false)
                .setFeatureFlag("pip.enabled", false)
                .setFeatureFlag("add-people.enabled", false)
                .setFeatureFlag("invite.enabled", false)
                .setFeatureFlag("chat.enabled",false)
                .setFeatureFlag("raise-hand.enabled", false)
                .setFeatureFlag("close-captions.enabled", false)
                .setFeatureFlag("raise-hand.enabled", false)
                .setFeatureFlag("welcomepage.enabled", false)
                .setFeatureFlag("fullscreen.enabled", true)
                    .build();
        }catch (Exception e){

        }
//

        PermissoNView();

    }

//    private void CheckCall() {
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                FirebaseDatabase   firebaseDatabase  = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
//                DatabaseReference databaseReference =firebaseDatabase.getReference();
//                databaseReference.child("Person").child(UID)
//                        .child("Call").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String s = snapshot.getValue(String.class);
//                        if(s.equalsIgnoreCase("Active")){
//                            FirebaseDatabase   firebaseDatabase  = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
//                            DatabaseReference databaseReference =firebaseDatabase.getReference();
//                            databaseReference.child("Person").child(UID)
//                                    .child("Call").setValue("Active").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                }
//                            });
//
//                             finish();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        },15000);
//
//
//
//    }

    private void PermissoNView() {
        if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},123);
        }else{
            jitsiMeetView.join(options1);
            jitsiMeetView.setListener(this);
            setContentView(jitsiMeetView);
        }
    }

    @Override
    public void requestPermissions(String[] strings, int i, PermissionListener permissionListener) {

    }



    @Override
    public void onConferenceTerminated(Map<String, Object> map) {

        Data data = new Data("1",TOKEN,"CANCLEOS","2332","2323","23");
        Notification notification = new Notification(data,TOKEN);
       APIServices apiServices = API.getCilent().create(APIServices.class);
        apiServices.sendRemoteMessage(notification).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response!=null){

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });



        finish();


    }

    @Override
    public void onConferenceJoined(Map<String, Object> data) {

    }

    @Override
    public void onConferenceWillJoin(Map<String, Object> data) {
//                Toast.makeText(MecpMeetActivity.this, "onConferenceWillJoin", Toast.LENGTH_SHORT).show();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(check_user ==0){
            FirebaseDatabase   firebaseDatabase  = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
            DatabaseReference databaseReference =firebaseDatabase.getReference();
            databaseReference.child("User").child(shareF.getUID())
                    .child("Call").setValue("Active").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }else{
            FirebaseDatabase   firebaseDatabase  = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
            DatabaseReference databaseReference =firebaseDatabase.getReference();
            databaseReference.child("Teacher").child(shareF.getUID())
                    .child("Call").setValue("Active").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }



    }
    private BroadcastReceiver getMessage  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String res= intent.getStringExtra("RESPONSIVE");
            Log.d("CHECKED",res);
            if(res.equalsIgnoreCase("CANCLEOS")){
                finish();
            }


        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(getMessage,new IntentFilter("RESPONSIVE"));
    }

    @Override
    protected void onStop() {
       super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getMessage);
    }
}
