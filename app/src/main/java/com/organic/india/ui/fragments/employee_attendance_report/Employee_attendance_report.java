package com.organic.india.ui.fragments.employee_attendance_report;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Team_list_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.team_listing.Employee;
import com.organic.india.pojo.team_listing.Team_listing;
import com.organic.india.singletone.Organic_india;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee_attendance_report extends Fragment {

    Functions_common functions_common;

    Team_list_adapter adapter;
    List<Employee> employees=new ArrayList<>();

    @BindView(R.id.rcy_list)RecyclerView rcy_list;
    @BindView(R.id.pb_progress)ProgressBar pb_progress;

    View view;

    Team_player team_player;
    public Employee_attendance_report(Team_player team_player){
        this.team_player = team_player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        view=  inflater.inflate(R.layout.fragment_employee_attendance_report, container, false);
        ButterKnife.bind(this,view);

        functions_common=new Functions_common(getContext());

        adapter=new Team_list_adapter(employees, new Team_list_adapter.Team_player(){
            @Override
            public void selected_player(Employee employee){
                team_player.selected_player(employee);
            }
        });
        rcy_list.setAdapter(adapter);

        fetch_team_list();


        return view;
    }

    void fetch_team_list(){

        employees.removeAll(employees);
        adapter.notifyDataSetChanged();

        pb_progress.setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id",Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("is_login",1);
        jsonObject.addProperty("month_year","2021-09");

        Log.e("params",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().team_listing(jsonObject)
                .enqueue(new Callback<Team_listing>() {
                    @Override
                    public void onResponse(Call<Team_listing> call, Response<Team_listing> response) {
                        if (response.isSuccessful()){

                            switch (response.body().getStatus()){

                                case 200:
                                    pb_progress.setVisibility(View.GONE);
                                    employees.addAll(response.body().getEmployees()!=null?response.body().getEmployees():employees);
                                    adapter.notifyDataSetChanged();
                                    break;

                                default:
                                    pb_progress.setVisibility(View.GONE);
                                    functions_common.toast("something went wrong");
                                    break;

                            }
                        }
                        Log.e("attendance_report",""+new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<Team_listing> call, Throwable t) {
                        pb_progress.setVisibility(View.GONE);
                    }
                });

    }

    public interface Team_player{
        void selected_player(Employee employee);
    }
}