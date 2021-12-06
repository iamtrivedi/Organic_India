package com.organic.india.ui.activites.policies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Policies_list_adapter;
import com.organic.india.async.Download_file;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.policy.Get_policy;
import com.organic.india.pojo.policy.Policy;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.webview.Webview_page;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Policies_page extends AppCompatActivity {

    @BindView(R.id.rcy_policy)RecyclerView rcy_policy;

    Functions_common functions_common;

    List<Policy> data = new ArrayList<>();
    Policies_list_adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies_page);
        ButterKnife.bind(this);
        functions_common = new Functions_common(this);

        adapter = new Policies_list_adapter(data, this, new Policies_list_adapter.View_pdf() {
            @Override
            public void url(String url) {
               new Download_file(new Download_file.Download_result() {
                   @Override
                   public void getPath(String path) {

                       startActivity(new Intent(Policies_page.this, Webview_page.class)
                               .putExtra("pdf_link",""+path));

                       Log.e("pdf_link","failed "+path);                   }

                   @Override
                   public void failed(String cause) {
                       Log.e("pdf_link","failed "+cause);
                   }
               },Policies_page.this).execute(url);
            }
        });
        rcy_policy.setLayoutManager(new LinearLayoutManager(this));
        rcy_policy.setAdapter(adapter);


        get_policies();

    }

    public void back_press(View view){
        onBackPressed();
    }

    void get_policies(){

        functions_common.show_loader("Getting Circulations");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());

        Api_instence.getRetrofitInstance().policies().enqueue(new Callback<Get_policy>() {
            @Override
            public void onResponse(Call<Get_policy> call, Response<Get_policy> response) {
                functions_common.dismiss_loader();
                data.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Get_policy> call, Throwable t) {
                functions_common.dismiss_loader();
            }
        });
    }
}