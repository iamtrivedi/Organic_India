package com.organic.india.ui.activites.whoswho;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Whoose_who_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.whoswho.GetWhoswho;
import com.organic.india.pojo.whoswho.Who;
import com.organic.india.singletone.Organic_india;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Whosewho_page extends AppCompatActivity {

    @BindView(R.id.rcy_list)RecyclerView rcy_list;


    Functions_common functions_common;

    List<Who> data=new ArrayList<>();
    Whoose_who_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whosewho_page);
        ButterKnife.bind(this);

        functions_common = new Functions_common(this);

        adapter = new Whoose_who_adapter(data,this);
        rcy_list.setLayoutManager(new GridLayoutManager(this, 2));
        rcy_list.setAdapter(adapter);

        get_whowho_data();

    }

    public void back_press(View view){
        onBackPressed();
    }


    void get_whowho_data(){

        functions_common.show_loader("Getting Departments");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());

        Api_instence.getRetrofitInstance().whos_who_title().enqueue(new Callback<GetWhoswho>() {
            @Override
            public void onResponse(Call<GetWhoswho> call, Response<GetWhoswho> response) {
                functions_common.dismiss_loader();
                data.addAll(response.body().getData()!=null?response.body().getData():data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetWhoswho> call, Throwable t) {
                functions_common.dismiss_loader();
            }
        });
    }
}