package com.organic.india.bottom_sheet;

import android.app.Dialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Atteandance_log_adapter;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.attendance_log.Attendance_log;
import com.organic.india.pojo.attendance_log.Log;
import com.organic.india.singletone.Organic_india;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_attendance_log_sheet extends BottomSheetDialogFragment {

    List<Log> logList = new ArrayList<>();
    Atteandance_log_adapter adapter;

    View view;
    Unbinder unbinder;

    @BindView(R.id.tv_title)TextView tv_title;
    @BindView(R.id.pb_progress)ProgressBar pb_progress;
    @BindView(R.id.rcy_list)RecyclerView rcy_list;
    @BindView(R.id.tv_list_status)TextView tv_list_status;

    String date;
    String employee_id,employee_code;
    public View_attendance_log_sheet(String date, String employee_id, String employee_code) {
        this.date = date;
        this.employee_id = employee_id;
        this.employee_code = employee_code;
    }

    @Override
    public void setupDialog(Dialog dialog, int style){
        View view = View.inflate(getContext(), R.layout.bottomsheet_view_attendance_log, null);
        unbinder = ButterKnife.bind(this, view);
        dialog.setContentView(view);
        ButterKnife.bind(this,view);

        adapter = new Atteandance_log_adapter(logList,getContext());
        rcy_list.setAdapter(adapter);

        tv_title.setText("Attendance Logs On "+date);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id",employee_id);
        jsonObject.addProperty("employee_code", employee_code);
        jsonObject.addProperty("attendance_date", date);

        Api_instence.getRetrofitInstance().attendance_logs(jsonObject)
                .enqueue(new Callback<Attendance_log>() {
                    @Override
                    public void onResponse(Call<Attendance_log> call, Response<Attendance_log> response) {
                      if (response.isSuccessful()){
                          logList.addAll(response.body().getLogs()!=null?response.body().getLogs():logList);
                          tv_list_status.setText(logList.isEmpty()?"No Logs found":"");
                          pb_progress.setVisibility(View.GONE);
                          adapter.notifyDataSetChanged();

                          android.util.Log.e("dkdkdk",""+new Gson().toJson(response.body()));

                      }else{
                          tv_list_status.setText("something went wrong.please reload again");
                          pb_progress.setVisibility(View.GONE);
                      }
                    }
                    @Override
                    public void onFailure(Call<Attendance_log> call, Throwable t) {
                        tv_list_status.setText("something went wrong. please reload again");
                        pb_progress.setVisibility(View.GONE);
                    }
                });
    }
}
