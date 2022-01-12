package com.school.schoolbustracking.View.Account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.UserModels;
import com.school.schoolbustracking.R;

public class ChangePassActivity  extends AppCompatActivity {
    SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        EditText editpassold =  findViewById(R.id.editPass1);
        EditText editpassnew =  findViewById(R.id.editPass2);
        sharedPreferences = getSharedPreferences("MENU", Context.MODE_PRIVATE);

        findViewById(R.id.btnupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passold = editpassold.getText().toString().trim();
                String passnew = editpassnew.getText().toString().trim();
                if(passold.length()>0){
                    if(passnew.length()>0){
                        if(sharedPreferences.getString("TASK", "").equalsIgnoreCase("Student")){

                           FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference()
                                   .child("User").child(new ShareF(ChangePassActivity.this).getUID())
                                   .addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                           UserModels userModels = snapshot.getValue(UserModels.class);
                                           if(userModels.getPassword().equalsIgnoreCase(passold)){
                                               FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference()
                                                       .child("User").child(new ShareF(ChangePassActivity.this).getUID())
                                                       .child("password").setValue(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(ChangePassActivity.this, "Sucess", Toast.LENGTH_SHORT).show();

                                                        finish();
                                                    }
                                                   }
                                               });
                                           }else{
                                               Toast.makeText(ChangePassActivity.this, "Pass old wrong", Toast.LENGTH_SHORT).show();

                                           }
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError error) {

                                       }
                                   });




                        }else{
                            FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference()
                                    .child("Teacher").child(new ShareF(ChangePassActivity.this).getUID())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            UserModels userModels = snapshot.getValue(UserModels.class);
                                            if(userModels.getPassword().equalsIgnoreCase(passold)){
                                                FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/").getReference()
                                                        .child("Teacher").child(new ShareF(ChangePassActivity.this).getUID())
                                                        .child("password").setValue(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(ChangePassActivity.this, "Sucess", Toast.LENGTH_SHORT).show();

                                                            finish();
                                                        }
                                                    }
                                                });
                                            }else{
                                                Toast.makeText(ChangePassActivity.this, "Pass old wrong", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }

                    }
                }

            }
        });
    }
}
