package com.school.schoolbustracking.View.Account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.R;

import java.util.concurrent.TimeUnit;

public class AuthPhoneActivity  extends AppCompatActivity {
    private  String mverification ="";
    private  PhoneAuthProvider.ForceResendingToken Mtoken;
    private EditText editphone,editverify;
    private Button btnverify,btndone;
    private LinearLayout lphone,lvery;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editphone = findViewById(R.id.editphone);
        editverify = findViewById(R.id.editvery);
        btndone = findViewById(R.id.btndone);
        btnverify = findViewById(R.id.btnverifone);
        lphone = findViewById(R.id.lphone);
        lvery = findViewById(R.id.lveri);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editphone.getText().toString().trim().length()>9 && editphone.getText().toString().trim().length()< 12){
                    startPhoneNumberVerification(editphone.getText().toString().trim());
                }else {
                    Toast.makeText(AuthPhoneActivity.this, "Phone >=10 && <=11", Toast.LENGTH_SHORT).show();
                }
            }
        });
     SharedPreferences sharedPreferences = getSharedPreferences("MENU", Context.MODE_PRIVATE);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editverify.getText().toString().trim();
                if(code.length()>=6){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mverification,code);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if(sharedPreferences.getString("TASK", "").equalsIgnoreCase("Student")){
                                    FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/")
                                            .getReference().child("User").child(new ShareF(AuthPhoneActivity.this).getUID())
                                            .child("phonenumber").setValue(editphone.getText().toString().trim());
                                }else{
                                    FirebaseDatabase.getInstance("https://schoolbus-2a9a1-default-rtdb.firebaseio.com/")
                                            .getReference().child("Teacher").child(new ShareF(AuthPhoneActivity.this).getUID())
                                            .child("phonenumber").setValue(editphone.getText().toString().trim());
                                }


                                Toast.makeText(AuthPhoneActivity.this   , "Sucess full", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(AuthPhoneActivity.this, "Code  Wrong", Toast.LENGTH_SHORT).show();
                                editverify.setText("");
                            }

                        }
                    });

                }else{
                    Toast.makeText(AuthPhoneActivity.this, "Code  ==  6 digits", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void startPhoneNumberVerification(String phone) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuthSettings firebaseAuthSettings = auth.getFirebaseAuthSettings();

        String zero = phone.substring(1,phone.length());
        String phone_change = "+84"+zero;
        Log.d("CHECKED",phone_change);


        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone_change)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId,forceResendingToken);
                        mverification = verificationId;
                        Mtoken = forceResendingToken;
                         lphone.setVisibility(View.GONE);
                       lvery.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.d("CHECKED",e.getMessage()+" ");
                    }


                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

}
