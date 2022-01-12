package com.school.schoolbustracking.View.Community;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.school.schoolbustracking.Adapter.ChatAdapter;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.ChatModels;
import com.school.schoolbustracking.Models.TeacherModel;
import com.school.schoolbustracking.Models.UserModels;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity  extends AppCompatActivity {
    private Toolbar toolbar;

    private Intent intent;
    private TextView txtname;
    private CircleImageView avatar;
    private TeacherModel userModels_teacher;
    private UserModels userModels_Student;
    private  RecyclerView rcvChat;
    private DatabaseReference databaseReference;
    private Button btnsend;
    private EditText editMessage;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatModels> arrayList;
    private MediaPlayer mediaPlayer;
    private  int check_user = 0 ;
    private  ShareF shareF;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InitWidget();
        Init();
        Chat();
    }

    private void Chat() {

    }

    private void Init() {

        mediaPlayer = MediaPlayer.create(this,R.raw.message);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent=getIntent();
         shareF = new ShareF(this);
        SharedPreferences sharedPreferences= getSharedPreferences("MENU", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("TASK","").equalsIgnoreCase("Student")){
            userModels_teacher  = (TeacherModel) intent.getSerializableExtra("ND");
            Picasso.with(this).load(userModels_teacher.getPhoto()).into(avatar);
            txtname.setText(userModels_teacher.getFullname());
        }else{
            check_user = 1;
           userModels_Student  = (UserModels) intent.getSerializableExtra("ND");
            Picasso.with(this).load(userModels_Student.getPhoto()).into(avatar);
            txtname.setText(userModels_Student.getFullname());
        }




         btnsend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String message = editMessage.getText().toString().trim();
                 if(message.length()>0){
                     if(check_user == 0){
                         SendMessage(userModels_teacher.getFullname(),userModels_teacher.getUid(),message);
                     }else{
                         SendMessage(userModels_Student.getFullname(),userModels_Student.getUid(),message);
                     }

                 }else{
                     Toast.makeText(ChatActivity.this,"Message not null",Toast.LENGTH_SHORT).show();
                 }
                 editMessage.setText("");

             }
         });
         rcvChat.setHasFixedSize(true);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
         linearLayoutManager.setStackFromEnd(true);
          rcvChat.setLayoutManager(linearLayoutManager);

        readMessage();





    }
    private  void SendMessage(String sender,String receiver,String message){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender", shareF.getUID());
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference();
         databaseReference.child("Chat")
                 .push().setValue(hashMap);
    }
    private  void readMessage(){

        String uid =shareF.getUID();
           arrayList=new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference();
        databaseReference.child("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for(DataSnapshot d : snapshot.getChildren()){
                    ChatModels chatModels = d.getValue(ChatModels.class);
                    if(check_user ==0) {
                        if (chatModels.getReceiver().equalsIgnoreCase(shareF.getUID())
                                && chatModels.getSender().equalsIgnoreCase(userModels_teacher.getUid()) ||
                                chatModels.getReceiver().equalsIgnoreCase(userModels_teacher.getUid())
                                        && chatModels.getSender().equalsIgnoreCase(uid)) {
                            arrayList.add(chatModels);
                        }
                    }else{
                        if (chatModels.getReceiver().equalsIgnoreCase(shareF.getUID())
                                && chatModels.getSender().equalsIgnoreCase(userModels_Student.getUid()) ||
                                chatModels.getReceiver().equalsIgnoreCase(userModels_Student.getUid())
                                        && chatModels.getSender().equalsIgnoreCase(uid)) {
                            arrayList.add(chatModels);
                        }
                    }

                   if(mediaPlayer.isPlaying()){

                   }else {
                       mediaPlayer.start();
                   }
                   if(check_user ==0 ){
                       chatAdapter =new ChatAdapter(ChatActivity.this,arrayList,userModels_teacher.getPhoto());
                   }else{
                       chatAdapter =new ChatAdapter(ChatActivity.this,arrayList,userModels_Student.getPhoto());
                   }

                    rcvChat.setAdapter(chatAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deletechat:
                ShareF shareF  =new ShareF(this);
                String uid= shareF.getUID();
               databaseReference.child("Chat").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       arrayList.clear();
                       for(DataSnapshot d : snapshot.getChildren()){
                           ChatModels chatModels = d.getValue(ChatModels.class);
                           if(check_user ==0) {
                               if (chatModels.getReceiver().equalsIgnoreCase(shareF.getUID())
                                       && chatModels.getSender().equalsIgnoreCase(userModels_teacher.getUid()) ||
                                       chatModels.getReceiver().equalsIgnoreCase(userModels_teacher.getUid())
                                               && chatModels.getSender().equalsIgnoreCase(uid)) {

                                   databaseReference.child("Chat").child(d.getKey()).setValue(null);
                               }
                           }else{
                               if (chatModels.getReceiver().equalsIgnoreCase(shareF.getUID())
                                       && chatModels.getSender().equalsIgnoreCase(userModels_Student.getUid()) ||
                                       chatModels.getReceiver().equalsIgnoreCase(userModels_Student.getUid())
                                               && chatModels.getSender().equalsIgnoreCase(uid)) {


                                   databaseReference.child("Chat").child(d.getKey()).setValue(null);
                               }
                           }
                           if(check_user ==0 ){
                               chatAdapter =new ChatAdapter(ChatActivity.this,arrayList,userModels_teacher.getPhoto());
                           }else{
                               chatAdapter =new ChatAdapter(ChatActivity.this,arrayList,userModels_Student.getPhoto());
                           }

                           rcvChat.setAdapter(chatAdapter);
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void InitWidget() {
        toolbar=findViewById(R.id.toolbar);
        rcvChat=findViewById(R.id.rcvChat);
        avatar=findViewById(R.id.hinhanh);
        txtname=findViewById(R.id.txtname);
        btnsend=findViewById(R.id.btnsend);
        editMessage= findViewById(R.id.editMessage);
        
    }
}
