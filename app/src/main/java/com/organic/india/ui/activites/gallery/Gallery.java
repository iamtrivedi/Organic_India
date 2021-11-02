package com.organic.india.ui.activites.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Gallery_folder_adapter;
import com.organic.india.adapter.Leave_chart_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.gallery_folder.Data;
import com.organic.india.pojo.gallery_folder.Gallery_folder;
import com.organic.india.pojo.pending_leave.Pending_leave;
import com.organic.india.singletone.Organic_india;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gallery extends AppCompatActivity {

    Gallery_folder_adapter adapter;
    List<Data> data = new ArrayList<>();

    Functions_common functions_common;

    @BindView(R.id.rcy_list)RecyclerView rcy_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        functions_common = new Functions_common(this);

        get_gallery_data();
    }



    void get_gallery_data(){

        functions_common.show_loader("Getting gallery");

        adapter = new Gallery_folder_adapter(data,this);
        rcy_list.setLayoutManager(new LinearLayoutManager(this));
        rcy_list.setAdapter(adapter);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());

        Api_instence.getRetrofitInstance().gallery_folders(jsonObject).enqueue(new Callback<Gallery_folder>() {
            @Override
            public void onResponse(Call<Gallery_folder> call, Response<Gallery_folder> response) {
               functions_common.dismiss_loader();
               data.addAll(response.body().getData()!=null?response.body().getData():data);
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Gallery_folder> call, Throwable t) {
               functions_common.dismiss_loader();
            }
        });
    }

    public void back_press(View view){
      onBackPressed();
    }
}