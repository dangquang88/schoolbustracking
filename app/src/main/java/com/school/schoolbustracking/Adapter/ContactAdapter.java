package com.school.schoolbustracking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.school.schoolbustracking.Models.UserModels;
import com.school.schoolbustracking.Presenter.SetOnItemClick;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.Community.CallActivity;
import com.school.schoolbustracking.View.Community.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.ViewHodler> {
    private Context context;
    private ArrayList<UserModels> arrayList;

    public ContactAdapter(Context context, ArrayList<UserModels> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stream_contact,parent,false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, @SuppressLint("RecyclerView") int position) {

        holder.SetOnItemClick(new SetOnItemClick() {
            @Override
            public void SetOnItemClick(View v, int pos) {

            }
        });
        holder.mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("ND",arrayList.get(position));
                context.startActivity(intent);
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CallActivity.class);
                intent.putExtra("ND",arrayList.get(position));
                context.startActivity(intent);
            }
        });
        UserModels userModels = arrayList.get(position);
        Picasso.with(context).load(userModels.getPhoto()).into(holder.avatar);
        holder.txtgender.setText(userModels.getGender());
        holder.txtname.setText(userModels.getFullname());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder  implements  View.OnClickListener{
        CircleImageView avatar;
        TextView txtname,txtgender;
        ImageView call,mess;
        SetOnItemClick itemClick;


        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            txtgender=itemView.findViewById(R.id.txtgender);
            txtname=itemView.findViewById(R.id.txtname);
            call = itemView.findViewById(R.id.call);
            mess = itemView.findViewById(R.id.message);
            itemView.setOnClickListener(this);
        }
   public  void SetOnItemClick(SetOnItemClick itemClick){
            this.itemClick= itemClick;
   }
        @Override
        public void onClick(View v) {
            itemClick.SetOnItemClick(v,getAdapterPosition());
        }
    }
}
