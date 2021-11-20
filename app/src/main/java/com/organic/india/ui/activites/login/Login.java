package com.organic.india.ui.activites.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.common.Functions_common;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.data.Api_Interfaces;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.logged_in_user.Logged_in_user;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Functions_common functions_common;

    @BindView(R.id.tv_proceed)TextView tv_proceed;
    @BindView(R.id.et_email)EditText et_email;
    @BindView(R.id.et_password)EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        functions_common=new Functions_common(this);

        tv_proceed.setOnClickListener(view -> {
          do_login();
        });
    }

    private void do_login(){

        String user_name=et_email.getText().toString();
        String password=et_password.getText().toString();

        if (user_name.isEmpty() || password.isEmpty()){
            functions_common.toast("email or password should't be blank");
            return;
        }

        functions_common.show_loader("Checking user");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email",user_name);
        jsonObject.addProperty("password",password);

        Log.e("params_send",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().employee_login(jsonObject).enqueue(new Callback<Logged_in_user>() {
            @Override
            public void onResponse(Call<Logged_in_user> call, Response<Logged_in_user> response){
              try {
                  if (response.isSuccessful() && response.body().getStatus().equals(200)){
                      Log.e("employee_login",""+new Gson().toJson(response.body()));


                      SharedPreferenceUtils.putString(SharedPreferenceUtils.User_Creds.user_creds,new Gson().toJson(response.body().getData()));
                      Organic_india.getInstance().setMe(response.body().getData());
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run(){
                              functions_common.dismiss_loader();
                              startActivity(new Intent(Login.this,MainActivity.class));
                              finishAffinity();
                          }
                      },2000);

                  }else{
                      functions_common.dismiss_loader();
                      functions_common.toast(response.body().getMessage());
                  }
              }catch (NullPointerException e){
                  functions_common.dismiss_loader();
                  functions_common.toast("something went wrong");
                  Log.e("employee_login","internal error "+e.getMessage());
              }

            }
            @Override
            public void onFailure(Call<Logged_in_user> call, Throwable t) {
              Log.e("employee_login",""+t.getMessage());
                functions_common.dismiss_loader();
              functions_common.fail_request();
            }
        });
    }
}