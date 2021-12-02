package com.organic.india.ui.activites.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.organic.india.R;
import com.organic.india.common.Functions_common;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.pojo.logged_in_user.Data;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.login.Login;
import com.organic.india.ui.activites.main.MainActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Splash_screen extends AppCompatActivity{

    List<String> listPermissionsNeeded = new ArrayList<>();

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        new Functions_common(Splash_screen.this, new Functions_common.Permission_handle() {
            @Override
            public void granted() {
                entry();
            }

            @Override
            public void denied() {
                finish();
                Toast.makeText(Splash_screen.this, "please give all permission", Toast.LENGTH_SHORT).show();
            }
        }).handle_permission(Splash_screen.this, permissions);

    }

    void  entry(){
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