package com.school.schoolbustracking.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.Account.SignInActivity;

public class MenuActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SharedPreferences sharedPreferences= getSharedPreferences("MENU",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        findViewById(R.id.btnstudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editor.putString("TASK","Student");
               editor.commit();
               startActivity(new Intent(MenuActivity.this, SignInActivity.class));
            }
        });
        findViewById(R.id.btnTeacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("TASK","Teacher");
                editor.commit();
                startActivity(new Intent(MenuActivity.this, SignInActivity.class));
            }
        });
    }
}
