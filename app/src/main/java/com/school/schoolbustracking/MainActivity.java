package com.school.schoolbustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.school.schoolbustracking.View.Account.SignInActivity;
import com.school.schoolbustracking.View.MenuActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= getSharedPreferences("MENU",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int k = sharedPreferences.getInt("K",0);


        if(k==0){
            editor.putString("TASK","");
            editor.putInt("K",1);
            editor.commit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
                }
            },1000);
        }else{
            String task  = sharedPreferences.getString("TASK","");
            if(task.length()>1){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this,SignInActivity.class));
                    }
                },2000);
            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    }
                },1000);
            }

        }



    }
}