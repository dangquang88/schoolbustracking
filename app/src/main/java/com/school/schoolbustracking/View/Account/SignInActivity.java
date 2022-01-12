package com.school.schoolbustracking.View.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Presenter.UserPreSenter;
import com.school.schoolbustracking.Presenter.UserView;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.HomeActivity;

public class SignInActivity extends AppCompatActivity implements UserView {
    private UserPreSenter userPreSenter;
    private EditText editUsername,editPass;
    private Button btnlogin;
    private ShareF shareF;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        InitWidget();
        shareF = new ShareF(this);
        userPreSenter = new UserPreSenter(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= editUsername.getText().toString().trim();
                String pass = editPass.getText().toString().trim();
                SharedPreferences sharedPreferences= getSharedPreferences("MENU",MODE_PRIVATE);
                String TASK = sharedPreferences.getString("TASK","");

                if(username.length()>0 ){
                    if(pass.length()>0){
                        if(TASK.equalsIgnoreCase("Student")){
                            userPreSenter.HandleLoginUser(username,pass);
                        }else{
                            userPreSenter.HandleLoginUserTeacher(username,pass);
                        }

                    }else{
                        Toast.makeText(SignInActivity.this, "Mật khẩu không để trống!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignInActivity.this, "Tài khoản không để trông !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void InitWidget() {
        editPass=findViewById(R.id.editPass);
        editUsername = findViewById(R.id.editUsername);
        btnlogin = findViewById(R.id.btnsignin);
    }

    @Override
    public void OnSucess(String uid) {
     shareF.PutUID(uid);
     startActivity(new Intent(SignInActivity.this, HomeActivity.class));

    }

    @Override
    public void OnFail(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataProfile(String uid, String address, String gender, String date_of_birth, String fullname, String grade, double lat, double lo, String tokenfcm, String photo, String uid_teacher, String phonenumber) {

    }

    @Override
    public void getDataproFileTeacher(String uid, String address, String gender, String date_of_birth, String fullname, String grade, double lat, double lo, String tokenfcm, String photo, String phonenumber) {

    }


}
