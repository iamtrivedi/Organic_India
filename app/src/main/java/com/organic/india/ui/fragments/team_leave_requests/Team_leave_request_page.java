package com.organic.india.ui.fragments.team_leave_requests;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Team_leave_request_adapter;
import com.organic.india.common.Constant;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.dialog.Accept_or_Reject_Leave;
import com.organic.india.dialog.Accept_or_Reject_attendance;
import com.organic.india.pojo.attendance_request.Request;
import com.organic.india.pojo.team_leave_request.Data;
import com.organic.india.pojo.team_leave_request.Team_leave_request;
import com.organic.india.singletone.Organic_india;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Team_leave_request_page extends Fragment implements View.OnClickListener {

    List<Data> leave_request=new ArrayList<>();
    Team_leave_request_adapter adapter;

    Functions_common functions_common;
    Calendar current_date = Calendar.getInstance();

    @BindView(R.id.currentDateLabel)TextView currentDateLabel;
    @BindView(R.id.previousButton)ImageView previousButton;
    @BindView(R.id.forwardButton)ImageView forwardButton;
    @BindView(R.id.rcy_attendance_report)RecyclerView rcy_attendance_report;
    @BindView(R.id.pb_progress)ProgressBar pb_progress;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_leave_request_page, container, false);
        ButterKnife.bind(this,view);

        functions_common=new Functions_common(getContext());

        adapter = new Team_leave_request_adapter(leave_request, getContext(), new Team_leave_request_adapter.Reaction() {
            @Override
            public void action(Data leave,int position) {

                new Accept_or_Reject_Leave(getContext(), leave, new Accept_or_Reject_Leave.React() {
                    @Override
                    public void reaction(int value) {

                        approve_or_reject_leave(leave,value,position);
                    }
                }).show();

            }
        });

        rcy_attendance_report.setAdapter(adapter);

        fetch_leave_request(current_date.getTime());

        previousButton.setOnClickListener(this::onClick);
        forwardButton.setOnClickListener(this::onClick);
        currentDateLabel.setOnClickListener(this::onClick);

        return view;
    }

    void fetch_leave_request(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Format formatter = new SimpleDateFormat("MMMM yyyy");

        currentDateLabel.setText(formatter.format(date));


        pb_progress.setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("leave_month", Constant.yyyy_mm(current_date));

        Log.e("params",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().team_leave_request(jsonObject)
                .enqueue(new Callback<Team_leave_request>() {
                    @Override
                    public void onResponse(Call<Team_leave_request> call, Response<Team_leave_request> response) {
                        if (response.isSuccessful()){
                            switch (response.body().getStatus()){

                                case 200:
                                    pb_progress.setVisibility(View.GONE);
                                    leave_request.removeAll(leave_request);
                                    leave_request.addAll(response.body().getData());
                                    adapter.notifyDataSetChanged();
                                    break;

                                default:
                                    functions_common.toast("no leave request found");
                                    pb_progress.setVisibility(View.GONE);
                                    leave_request.removeAll(leave_request);
                                    adapter.notifyDataSetChanged();
                                    break;

                            }
                        }else{
                            pb_progress.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<Team_leave_request> call, Throwable t) {
                        pb_progress.setVisibility(View.GONE);
                        functions_common.fail_request();
                    }
                });
    }


    void approve_or_reject_leave(Data leave, int value, int position){

        functions_common.show_loader(value==1?"Leaving Request Accepting":"Leaving Request Rejecting");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id",Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code",Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("leave_id",leave.getLeaveId());
        jsonObject.addProperty("accept_reject",value);

        Api_instence.getRetrofitInstance().leave_accept_reject(jsonObject)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){

                        String res_str="";

                        if (response.isSuccessful()){
                            try {
                                res_str = response.body().string();
                                JSONObject obj = new JSONObject(res_str);
                                switch (obj.getInt("status")){

                                    case 200:
                                        leave_request.remove(position);
                                        adapter.notifyDataSetChanged();
                                        break;

                                    default:
                                        functions_common.toast(obj.getString("message"));
                                        break;

                                }
                                functions_common.dismiss_loader();

                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            functions_common.dismiss_loader();
                            functions_common.toast(response.message());
                        }
                        Log.e("approve_reject",res_str);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        functions_common.dismiss_loader();
                        functions_common.toast("sorry ! couldn't connect to server");
                    }
                });

    }

    void change_to_pre_month(){
        current_date.add(Calendar.MONTH, -1);
        fetch_leave_request(current_date.getTime());

    }

    void change_to_post_month(){
        current_date.add(Calendar.MONTH, 1);
        fetch_leave_request(current_date.getTime());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.currentDateLabel:
                choose_year_month();
                break;

            case R.id.previousButton:
                change_to_pre_month();
                break;

            case R.id.forwardButton:
                change_to_post_month();
                break;
        }
    }


    private MonthYearPickerDialogFragment createDialogWithRanges(boolean customTitle) {
        final int minYear = 1900;
        final int maxYear = 2100;
        final int maxMoth = 11;
        final int minMoth = 0;
        final int minDay = 1;
        final int maxDay = 31;
        long minDate;
        long maxDate;

        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        calendar.set(minYear, minMoth, minDay);
        minDate = calendar.getTimeInMillis();

        calendar.clear();
        calendar.set(maxYear, maxMoth, maxDay);
        maxDate = calendar.getTimeInMillis();

        return MonthYearPickerDialogFragment
                .getInstance(current_date.get(Calendar.MONTH),
                        current_date.get(Calendar.YEAR),
                        minDate,
                        maxDate,
                        customTitle ? getString(R.string.select_month).toUpperCase() : null);
    }

    void choose_year_month(){

        MonthYearPickerDialogFragment dialogFragment = createDialogWithRanges(true);

        dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int monthOfYear) {

                current_date.set(year,monthOfYear+1,0);
                fetch_leave_request(current_date.getTime());

            }
        });

        dialogFragment.show(getChildFragmentManager(), null);
    }
}