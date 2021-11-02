package com.organic.india.ui.fragments.change_password;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.singletone.Organic_india;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Change_password extends Fragment implements View.OnClickListener {

    View view;
    Unbinder unbinder;

    Functions_common functions_common;

    @BindView(R.id.tv_cancel)TextView tv_cancel;
    @BindView(R.id.tv_update_password)TextView tv_update_password;
    @BindView(R.id.et_newpassword)EditText et_newpassword;
    @BindView(R.id.et_confirmpassword)EditText et_confirmpassword;

    Actions actions;
    public Change_password(Actions actions) {
        this.actions = actions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder= ButterKnife.bind(this,view);

        functions_common =new Functions_common(getContext());

        tv_cancel.setOnClickListener(this::onClick);
        tv_update_password.setOnClickListener(this::onClick);

        return view;
    }


    void do_update_password(){
        if (et_confirmpassword.getText().toString().isEmpty() || et_newpassword.getText().toString().isEmpty()){

          functions_common.toast("both fields shouldn't be empty");

        }else if(TextUtils.equals(et_newpassword.getText().toString(),et_confirmpassword.getText().toString())){

            update_password();

        }else{

            functions_common.toast("passwords are not matching");
        }
    }

    private void update_password(){

        functions_common.show_loader("Changing password");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code",Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("new_password",et_newpassword.getText().toString());
        jsonObject.addProperty("confirm_password",et_confirmpassword.getText().toString());

        Log.e("change_password",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().change_password(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              if (response.isSuccessful()){
                  try {
                      JSONObject obj = new JSONObject(response.body().string());
                      switch (obj.getInt("status")){

                          case 200:
                              functions_common.toast(obj.getString("message"));
                              break;

                          default:
                              functions_common.toast(obj.getString("message"));
                              break;
                      }
                  } catch (JSONException e) {
                      Log.e("change_password"," internal error "+e.getMessage());
                      e.printStackTrace();
                  } catch (IOException e) {
                      Log.e("change_password"," internal error "+e.getMessage());
                      e.printStackTrace();
                  }
              }else{
                  functions_common.toast("something went wrong");
              }
                functions_common.dismiss_loader();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
             functions_common.dismiss_loader();
             functions_common.fail_request();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){

         case R.id.tv_update_password:
             do_update_password();
             break;

         case R.id.tv_cancel:
             actions.on_cancel();
             break;
       }
    }

    public interface Actions{
        void on_update();
        void on_cancel();
    }
}