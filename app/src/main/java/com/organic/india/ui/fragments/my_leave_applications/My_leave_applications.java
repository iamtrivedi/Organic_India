package com.organic.india.ui.fragments.my_leave_applications;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.My_leave_list_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.leave_applications.Application;
import com.organic.india.pojo.leave_applications.Leave_application;
import com.organic.india.singletone.Organic_india;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_leave_applications extends Fragment{

    My_leave_list_adapter adapter;

    Functions_common functions_common;
    List<Application> applications= new ArrayList<>();

    View view;
    @BindView(R.id.rcy_list)RecyclerView rcy_list;


    public My_leave_applications(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.fragment_my_leave_applications, container, false);
        ButterKnife.bind(this,view);

        functions_common=new Functions_common(getContext());

        get_my_leave_applications();

        adapter=new My_leave_list_adapter(getContext(),applications);
        rcy_list.setAdapter(adapter);

        return view;
    }


    void get_my_leave_applications(){

        functions_common.show_loader("Getting Leaving Applications");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());

        Log.e("params",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().leave_applications(jsonObject)
                .enqueue(new Callback<Leave_application>() {
                    @Override
                    public void onResponse(Call<Leave_application> call, Response<Leave_application> response) {
                        if (response.isSuccessful()){
                           applications.addAll(response.body().getData()!=null?response.body().getData():applications);
                           adapter.notifyDataSetChanged();
                            functions_common.dismiss_loader();
                        }else{
                            functions_common.dismiss_loader();
                            functions_common.toast("something went wrong");
                        }
                    }
                    @Override
                    public void onFailure(Call<Leave_application> call, Throwable t) {
                     functions_common.dismiss_loader();
                     functions_common.toast("sorry! couldn't make a request");
                    }
                });
    }
}