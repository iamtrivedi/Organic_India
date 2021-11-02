package com.organic.india.ui.activites.splash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.organic.india.R;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.pojo.logged_in_user.Data;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.login.Login;
import com.organic.india.ui.activites.main.MainActivity;

public class Splash_screen extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (SharedPreferenceUtils.getString(SharedPreferenceUtils.User_Creds.user_creds,"").isEmpty() || SharedPreferenceUtils.getString(SharedPreferenceUtils.User_Creds.user_creds,"")==null){

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    startActivity(new Intent(Splash_screen.this, Login.class));
                    finishAffinity();
                }
            },2000);

        }else{

            Log.e("creds",""+SharedPreferenceUtils.getString(SharedPreferenceUtils.User_Creds.user_creds,""));

            Organic_india.getInstance().setMe(new Gson().fromJson(SharedPreferenceUtils.getString(SharedPreferenceUtils.User_Creds.user_creds,SharedPreferenceUtils.User_Creds.user_creds), Data.class));
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    startActivity(new Intent(Splash_screen.this, MainActivity.class));
                    finishAffinity();
                }
            },2000);
        }
    }
}