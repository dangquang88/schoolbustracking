package com.school.schoolbustracking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.ChatModels;
import com.school.schoolbustracking.Models.TeacherModel;
import com.school.schoolbustracking.Models.UserModels;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHodler> {
    private Context context;
    private ArrayList<ChatModels> arrayList;
    private  String image_url="";
    private  String image_default ="";
    private  static  final  int MSG_TYPE_LEFT=0;
    private  static  final  int MSG_TYPE_RIGHT=1;
    private  static  int check = 0;
    private ShareF shareF;




    public ChatAdapter(Context context, ArrayList<ChatModels> arrayList,String image_url) {
        this.context = context;
        this.arrayList = arrayList;
        this.image_url = image_url;
        shareF = new ShareF(context);
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType == MSG_TYPE_RIGHT){
            check =1;
            View view = LayoutInflater.from(context).inflate(R.layout.chat_send,parent,false);
            return new ViewHodler(view);
        }else {
            check =0;
            View view = LayoutInflater.from(context).inflate(R.layout.chat_receiver,parent,false);
            return new ViewHodler(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, @SuppressLint("RecyclerView") int position) {


         Picasso.with(context).load(image_url).into(holder.circleImageView);

         holder.txtmessage.setText(arrayList.get(position).getMessage());
        ShareF shareF = new ShareF(context);
        SharedPreferences sharedPreferences= context.getSharedPreferences("MENU", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("TASK","").equalsIgnoreCase("Student")){
            if(check==1){
            if(image_default.length()>1){
                Picasso.with(context).load(image_default).into(holder.circleImageView);
            }else{
                DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference();
                databaseReference.child("User").child(shareF.getUID())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModels userModels = snapshot.getValue(UserModels.class);
                                image_default = userModels.getPhoto();
                                Picasso.with(context).load(userModels.getPhoto()).into(holder.circleImageView);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }

        }else{
            Picasso.with(context).load(image_url).into(holder.circleImageView);
        }

            }else{
            if(check==1){
                if(image_default.length()>1){
                    Picasso.with(context).load(image_default).into(holder.circleImageView);
                }else{
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference();
                    databaseReference.child("Teacher").child(shareF.getUID())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    TeacherModel userModels = snapshot.getValue(TeacherModel.class);
                                    image_default = userModels.getPhoto();
                                    Picasso.with(context).load(userModels.getPhoto()).into(holder.circleImageView);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }

            }else{
                Picasso.with(context).load(image_url).into(holder.circleImageView);
            }

        }





    }

    @Override
    public int getItemViewType(int position) {

        if(arrayList.get(position).getSender().equalsIgnoreCase(shareF.getUID())){
            return  MSG_TYPE_RIGHT;
        }else{
            return  MSG_TYPE_LEFT;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        private  TextView txtmessage;
        private  CircleImageView circleImageView;



        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtmessage= itemView.findViewById(R.id.txtmessage);
            circleImageView= itemView.findViewById(R.id.image_url);

        }

        }

}
