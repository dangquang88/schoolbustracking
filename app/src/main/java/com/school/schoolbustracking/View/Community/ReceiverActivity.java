package com.school.schoolbustracking.View.Community;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.airbnb.lottie.LottieAnimationView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.FCM.API;
import com.school.schoolbustracking.Models.Data;
import com.school.schoolbustracking.Models.Notification;
import com.school.schoolbustracking.Models.UserModels;
import com.school.schoolbustracking.Presenter.APIServices;
import com.school.schoolbustracking.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiverActivity extends AppCompatActivity {
    private Intent intent;
    private UserModels userModels;
    private CircleImageView circleImageView;
    private TextView txtname;
    private LottieAnimationView Image_Cancle,ImageAcept;
    private  String Room="";
    private  String Token="";
    private APIServices apiServices;
    private String Photo="";
    private  String Name="";
    private  String UID="";
    private ShareF shareConfig;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        InitWidget();
        Init();
    }

    private void Init() {
        mediaPlayer = MediaPlayer.create(this,R.raw.call_in);
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }

        shareConfig = new ShareF(this);
        intent=getIntent();
        Room = intent.getStringExtra("ROOM");
        Token = intent.getStringExtra("TOKEN");
        Photo = intent.getStringExtra("PHOTO");
        Name = intent.getStringExtra("NAME");
        UID = intent.getStringExtra("UID");
        Picasso.with(this).load(Photo).into(circleImageView);
        txtname.setText(Name);

        Image_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                 
                    mediaPlayer.stop();
                }
                CancelCall();

            }
        });

        ImageAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = new Data("RESPONSIVE",Token,"Acept","","",shareConfig.getUID());
                Notification notification = new Notification(data,Token);
                apiServices = API.getCilent().create(APIServices.class);
                apiServices.sendRemoteMessage(notification).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                if(mediaPlayer.isPlaying()){
                 
                    mediaPlayer.stop();
                }
                AceptCall();
                finish();
            }
        });

    }

    private void AceptCall() {
        UpdateCall();
        Intent intent = new Intent(ReceiverActivity.this,VideoCallActivity.class);
        intent.putExtra("ROOM",Room);
        intent.putExtra("TOKEN",Token);
        intent.putExtra("VIDEO","K");
        intent.putExtra("UID",UID);
        startActivity(intent);
        finish();
    }

    private void CancelCall() {
        Data data = new Data("1",Token,"CANCLE","2332","2323","23");
        Notification notification = new Notification(data,Token);
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

    private void InitWidget() {
        circleImageView = findViewById(R.id.circle_imageview);
        txtname = findViewById(R.id.txtname);
        Image_Cancle  = findViewById(R.id.lottieCancel);
        ImageAcept = findViewById(R.id.littieAcept);

    }
    private BroadcastReceiver getMessage  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String res= intent.getStringExtra("RESPONSIVE");
            Log.d("CHECKED",res);
            if(res.equalsIgnoreCase("CANCLE")){
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
    private void UpdateCall() {
      FirebaseDatabase  firebaseDatabase  = FirebaseDatabase.getInstance("https://datingapp-7f7bb-default-rtdb.firebaseio.com/");
      DatabaseReference databaseReference =firebaseDatabase.getReference();
        databaseReference.child("Person").child(shareConfig.getUID())
                .child("Call").setValue("Busy").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getMessage);
    }
}
