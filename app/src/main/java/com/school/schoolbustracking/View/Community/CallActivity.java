package com.school.schoolbustracking.View.Community;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.airbnb.lottie.LottieAnimationView;
;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.school.schoolbustracking.Models.TeacherModel;
import com.school.schoolbustracking.Models.UserModels;
import com.school.schoolbustracking.Presenter.APIServices;
import com.school.schoolbustracking.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallActivity extends AppCompatActivity {
    private Intent intent;
    private UserModels userModels;
    private TeacherModel teacherModel;
    private CircleImageView circleImageView;
    private TextView txtname;
    private LottieAnimationView lottieAnimationView;
    private APIServices apiServices;
    private  String Room="";
    private ShareF shareF;
    private  FirebaseDatabase firebaseDatabase;
    private  DatabaseReference databaseReference;
    private MediaPlayer mediaPlayer;
    private  int check_user = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        circleImageView = findViewById(R.id.circle_imageview);
        txtname= findViewById(R.id.txtname);
        lottieAnimationView= findViewById(R.id.lottieCancel);
        mediaPlayer = MediaPlayer.create(this,R.raw.call_ount);
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }

        intent=getIntent();
        SharedPreferences sharedPreferences= getSharedPreferences("MENU", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("TASK","").equalsIgnoreCase("Student")){
            teacherModel  = (TeacherModel) intent.getSerializableExtra("ND");
            Picasso.with(this).load(teacherModel.getPhoto()).into(circleImageView);
            txtname.setText(teacherModel.getFullname());
        }else{
            check_user = 1;
            userModels  = (UserModels) intent.getSerializableExtra("ND");
            Picasso.with(this).load(userModels.getPhoto()).into(circleImageView);
            txtname.setText(userModels.getFullname());

        }

        /// Custom API services Call

        apiServices= API.getCilent().create(APIServices.class);
        Room= "Call student and teacher "+System.currentTimeMillis();
        shareF = new ShareF(this);
        databaseReference = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference();
        if(check_user ==0){
            databaseReference.child("Teacher").child(teacherModel.getUid())
                    .child("Call").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s = dataSnapshot.getValue(String.class);

                    if(s.equalsIgnoreCase("Active")){

                        databaseReference.child("User").child(shareF.getUID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModels userModels1 = snapshot.getValue(UserModels.class);
                                Data data  = new Data(Room,shareF.getToken(),"Invite",userModels1.getPhoto(),userModels1.getFullname(),userModels1.getUid());
                                Notification notification = new Notification(data,teacherModel.getTokenfcm());
                                UpdateCall();
                                apiServices.sendRemoteMessage(notification).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        UpdateCall();

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }else{
                        Toast.makeText(CallActivity.this, "User have call Another !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }else{
            databaseReference.child("User").child(userModels.getUid())
                    .child("Call").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s = dataSnapshot.getValue(String.class);
                    if(s.equalsIgnoreCase("Active")){

                        databaseReference.child("Teacher").child(shareF.getUID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                TeacherModel userModels1 = snapshot.getValue(TeacherModel.class);
                                Data data  = new Data(Room,shareF.getToken(),"Invite",userModels1.getPhoto(),userModels1.getFullname(),userModels1.getUid());
                                Notification notification = new Notification(data,userModels.getTokenfcm());
                                UpdateCall();
                                apiServices.sendRemoteMessage(notification).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        UpdateCall();

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }else{
                        Toast.makeText(CallActivity.this, "User have call Another !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }






//
        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase  = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
                databaseReference =firebaseDatabase.getReference();
                if(check_user==0){
                    databaseReference.child("User").child(shareF.getUID())
                            .child("Call").setValue("Active").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                            }
                            Data data = new Data("1",shareF.getToken(),"CANCLE","2332","2323","23");
                            Notification notification = new Notification(data,teacherModel.getTokenfcm());
                            apiServices = API.getCilent().create(APIServices.class);
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
                    });

                }else{
                    databaseReference.child("Teacher").child(shareF.getUID())
                            .child("Call").setValue("Active").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                            }
                            Data data = new Data("1",shareF.getToken(),"CANCLE","2332","2323","23");
                            Notification notification = new Notification(data,userModels.getTokenfcm());
                            apiServices = API.getCilent().create(APIServices.class);
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
                    });
                }

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firebaseDatabase  = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
                databaseReference =firebaseDatabase.getReference();
                if(check_user==0){
                    databaseReference.child("User").child(shareF.getUID())
                            .child("Call").setValue("Active").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                            }
                            Data data = new Data("1",shareF.getToken(),"CANCLE","2332","2323","23");
                            Notification notification = new Notification(data,teacherModel.getTokenfcm());
                            apiServices = API.getCilent().create(APIServices.class);
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
                    });

                }else{
                    databaseReference.child("Teacher").child(shareF.getUID())
                            .child("Call").setValue("Active").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                            }
                            Data data = new Data("1",shareF.getToken(),"CANCLE","2332","2323","23");
                            Notification notification = new Notification(data,userModels.getTokenfcm());
                            apiServices = API.getCilent().create(APIServices.class);
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
                    });
                }


            }
        },15000);


    }

    private void UpdateCall() {
        if (check_user == 0) {
            firebaseDatabase = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("User").child(shareF.getUID())
                    .child("Call").setValue("Busy").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        } else {
            firebaseDatabase = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/");
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Teacher").child(shareF.getUID())
                    .child("Call").setValue("Busy").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });


        }
    }

    private  BroadcastReceiver getMessage  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String res= intent.getStringExtra("RESPONSIVE");
            Log.d("CHECKED",res);
            if(res.equalsIgnoreCase("Acept")){
                if(mediaPlayer.isPlaying()){

                    mediaPlayer.stop();
                }
                Intent intent1 = new Intent(CallActivity.this,VideoCallActivity.class);
                intent1.putExtra("ROOM",Room);
                if(check_user==0){
                    intent1.putExtra("TOKEN",teacherModel.getTokenfcm());
                }else{
                    intent1.putExtra("TOKEN",userModels.getTokenfcm());
                }

                intent1.putExtra("VIDEO","SENDER");
                if(check_user==0){
                    intent1.putExtra("UID",teacherModel.getUid());
                }else{
                    intent1.putExtra("UID",userModels.getUid());
                }
                finish();
                startActivity(intent1);
            }else if(res.equalsIgnoreCase("CANCLE")){
                if(mediaPlayer.isPlaying()){

                    mediaPlayer.stop();
                }
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

    @Override
    public void onBackPressed() {

    }
}
