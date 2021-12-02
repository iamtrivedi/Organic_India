package com.organic.india.bottom_sheet;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.common.Functions_common;
import com.organic.india.pojo.attendance_report.AttendanceReport;
import com.organic.india.singletone.Organic_india;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Request_add_atendance extends BottomSheetDialogFragment implements View.OnClickListener {

    //PARAMS
    String employee_id= ""+Organic_india.getInstance().getMe().getEmployeeId();
    String employee_code=""+Organic_india.getInstance().getMe().getEmployeeCode();
    String in_time_request="10:00:00 AM";
    String out_time_request="7:00:00 PM";
    String attendance_date="";
    String in_time_ip="";
    String reason="";

    Calendar start_time_cal = Calendar.getInstance();
    Calendar end_time_cal = Calendar.getInstance();

    AttendanceReport report=new AttendanceReport();

    Functions_common functions_common;

    View view;
    Unbinder unbinder;


    @BindView(R.id.ll_option)RelativeLayout ll_option;
    @BindView(R.id.tv_selected_option)TextView tv_selected_option;
    @BindView(R.id.ll_option_list)LinearLayout ll_option_list;
    @BindView(R.id.tv_forget_to_mark)TextView tv_forget_to_mark;
    @BindView(R.id.tv_with_client)TextView tv_with_client;
    @BindView(R.id.tv_start_time)TextView tv_start_time;
    @BindView(R.id.tv_end_time)TextView tv_end_time;
    @BindView(R.id.tv_submit)TextView tv_submit;
    @BindView(R.id.tv_cancel)TextView tv_cancel;
    @BindView(R.id.tv_title)TextView tv_title;
    @BindView(R.id.a_in_time_req)TextView a_in_time_req;
    @BindView(R.id.a_out_time_req)TextView a_out_time_req;
    @BindView(R.id.tv_on_duty)TextView tv_on_duty;
    @BindView(R.id.tv_work_from_home)TextView tv_work_from_home;

    Action action;
    public Request_add_atendance(String attendance_date,AttendanceReport report,Action action){
        this.attendance_date = attendance_date;
        this.report = report;
        this.action = action;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View view = View.inflate(getContext(), R.layout.bottomsheet_request_add_atendance, null);
        unbinder = ButterKnife.bind(this, view);
        dialog.setContentView(view);

        Log.e("times","start "+report.getActualInTime()+" end "+report.getActualOutTime());

        tv_title.setText("Attendance Request From "+Organic_india.getInstance().getMe().getName()+" Date "+attendance_date);

        functions_common=new Functions_common(getContext());

        in_time_ip=functions_common.getLocalIpAddress();


        in_time_request = report.getActualInTime().isEmpty()?"10:00:00 AM":report.getActualInTime();
        out_time_request = report.getActualOutTime().isEmpty()?"7:00:00 PM":report.getActualOutTime();

        tv_start_time.setText(in_time_request);
        tv_end_time.setText(out_time_request);

        a_in_time_req.setText(report.getActualInTime().isEmpty()?"Actual In Time : not found":"Actual In Time : "+report.getActualInTime());
        a_out_time_req.setText(report.getActualOutTime().isEmpty()?"Actual Out Time : not found":"Actual Out Time : "+report.getActualOutTime());


        SimpleDateFormat format = new SimpleDateFormat("h:mm:ss aa");
        try {

            Date time_start = format.parse(in_time_request);
            Date time_end = format.parse(out_time_request);

            start_time_cal.setTime(time_start);
            end_time_cal.setTime(time_end);

        } catch (ParseException e) {
            Log.e("any_exe",""+e.getMessage());
            e.printStackTrace();
            tv_start_time.setText("-");
            tv_end_time.setText("-");
        }



        tv_end_time.setOnClickListener(this::onClick);
        tv_start_time.setOnClickListener(this::onClick);
        ll_option.setOnClickListener(this::onClick);
        tv_forget_to_mark.setOnClickListener(this::onClick);
        tv_with_client.setOnClickListener(this::onClick);
        tv_submit.setOnClickListener(this::onClick);
        tv_cancel.setOnClickListener(this::onClick);
        tv_work_from_home.setOnClickListener(this::onClick);
        tv_on_duty.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){


           case R.id.tv_end_time:

               TimePickerDialog picker_end = new TimePickerDialog(getContext(),
                       new TimePickerDialog.OnTimeSetListener(){
                           @Override
                           public void onTimeSet(TimePicker tp, int sHour, int sMinute){

                               end_time_cal.set(end_time_cal.get(Calendar.YEAR), end_time_cal.get(Calendar.MONTH), end_time_cal.get(Calendar.DATE), sHour, sMinute, 0);
                               long timeInMillis = end_time_cal.getTimeInMillis();
                               Date date_c = new Date();
                               date_c.setTime(timeInMillis);
                               end_time_cal.setTime(date_c);

                               if (end_time_cal.after(start_time_cal)){

                                   SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss aa");
                                   out_time_request = sdf.format(end_time_cal.getTime());
                                   tv_end_time.setText(out_time_request);

                               }else{
                                 functions_common.toast("selected invalid time");
                               }
                               Log.e("selected_time","end "+end_time_cal.getTime());

                           }
                       }, end_time_cal.get(Calendar.HOUR_OF_DAY), end_time_cal.get(Calendar.MINUTE), false);
               picker_end.show();
               break;

           case R.id.tv_start_time:

               TimePickerDialog picker_star_time = new TimePickerDialog(getContext(),
                       new TimePickerDialog.OnTimeSetListener(){
                           @Override
                           public void onTimeSet(TimePicker tp, int sHour, int sMinute){

                               start_time_cal.set(start_time_cal.get(Calendar.YEAR), start_time_cal.get(Calendar.MONTH), start_time_cal.get(Calendar.DATE), sHour, sMinute, 0);
                               long timeInMillis = start_time_cal.getTimeInMillis();
                               Date date_c = new Date();
                               date_c.setTime(timeInMillis);
                               start_time_cal.setTime(date_c);

                               if (start_time_cal.before(end_time_cal)){

                                   SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss aa");
                                   in_time_request = sdf.format(start_time_cal.getTime());
                                   tv_start_time.setText(in_time_request);

                               }else{
                                   functions_common.toast("selected invalid time");
                               }
                               Log.e("selected_time","start "+start_time_cal.getTime());

                           }
                       }, start_time_cal.get(Calendar.HOUR_OF_DAY), start_time_cal.get(Calendar.MINUTE), false);
               picker_star_time.show();
               break;



           case R.id.ll_option:
               ll_option_list.setVisibility(ll_option_list.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
               break;

           case R.id.tv_forget_to_mark:
               reason="1";
               tv_selected_option.setText(tv_forget_to_mark.getText().toString());
               ll_option_list.setVisibility(View.GONE);
               break;

           case R.id.tv_with_client:
               reason="2";
               tv_selected_option.setText(tv_with_client.getText().toString());
               ll_option_list.setVisibility(View.GONE);
               break;

           case R.id.tv_on_duty:
               reason="3";
               tv_selected_option.setText(tv_on_duty.getText().toString());
               ll_option_list.setVisibility(View.GONE);
               break;

           case R.id.tv_work_from_home:
               reason="4";
               tv_selected_option.setText(tv_work_from_home.getText().toString());
               ll_option_list.setVisibility(View.GONE);
               break;

           case R.id.tv_submit:
               submit_data();
               break;

           case R.id.tv_cancel:
               this.dismiss();
               break;

       }
    }

    void submit_data(){

        if (reason.isEmpty()){
            functions_common.toast("please select reason");
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code",Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("in_time_request",in_time_request);
        jsonObject.addProperty("out_time_request", out_time_request);
        jsonObject.addProperty("attendance_date", attendance_date);
        jsonObject.addProperty("in_time_ip", in_time_ip);
        jsonObject.addProperty("reason", reason);

        action.update_attendance(jsonObject);

        this.dismiss();

    }

    public interface  Action{
        void update_attendance(JsonObject jsonObject);
    }
}
