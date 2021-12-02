package com.organic.india.ui.activites.whose_department_page;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Whoosewhoemployee_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.whoswhoemployee.Employee;
import com.organic.india.pojo.whoswhoemployee.WhoWhos_employee;
import com.organic.india.singletone.Organic_india;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Whose_department_page extends AppCompatActivity {

    String id="";

    List<Employee> data = new ArrayList<>();
    Whoosewhoemployee_adapter adapter;

    Functions_common functions_common;

    @BindView(R.id.tv_title)TextView tv_title;
    @BindView(R.id.rcy_list)RecyclerView rcy_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whose_department_page);
        ButterKnife.bind(this);
        functions_common = new Functions_common(this);

        id=getIntent().getStringExtra("ID");

        tv_title.setText(getIntent().getStringExtra("TITLE"));

        adapter= new Whoosewhoemployee_adapter(data,this);
        rcy_list.setLayoutManager(new LinearLayoutManager(this));
        rcy_list.setAdapter(adapter);

        get_whowho_employee();
    }

    public void back_press(View view){
        onBackPressed();
    }


    void get_whowho_employee(){

        functions_common.show_loader("Getting Departments");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("department_id", id);

        Api_instence.getRetrofitInstance().whos_who_title_employees(jsonObject).enqueue(new Callback<WhoWhos_employee>() {
            @Override
            public void onResponse(Call<WhoWhos_employee> call, Response<WhoWhos_employee> response) {
                functions_common.dismiss_loader();
                data.addAll(response.body().getData()!=null?response.body().getData():data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<WhoWhos_employee> call, Throwable t) {
                functions_common.dismiss_loader();
            }
        });
    }
}