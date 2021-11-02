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
import com.organic.india.singletone.Organic_india;
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
    String out_time_request="7:00:00 AM";
    String attendance_date="";
    String in_time_ip="";
    String reason="";

    Functions_common functions_common;
    Calendar calendar = Calendar.getInstance();

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

    Action action;
    public Request_add_atendance(String attendance_date,Action action){
        this.attendance_date = attendance_date;
        this.action = action;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View view = View.inflate(getContext(), R.layout.bottomsheet_request_add_atendance, null);
        unbinder = ButterKnife.bind(this, view);
        dialog.setContentView(view);

        tv_title.setText("Attendance Request From "+Organic_india.getInstance().getMe().getName()+" Date "+attendance_date);

        functions_common=new Functions_common(getContext());

        in_time_ip=functions_common.getLocalIpAddress();


        tv_end_time.setOnClickListener(this::onClick);
        tv_start_time.setOnClickListener(this::onClick);
        ll_option.setOnClickListener(this::onClick);
        tv_forget_to_mark.setOnClickListener(this::onClick);
        tv_with_client.setOnClickListener(this::onClick);
        tv_submit.setOnClickListener(this::onClick);
        tv_cancel.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){


           case R.id.tv_end_time:

               TimePickerDialog picker_end = new TimePickerDialog(getContext(),
                       new TimePickerDialog.OnTimeSetListener(){
                           @Override
                           public void onTimeSet(TimePicker tp, int sHour, int sMinute){

                               calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), sHour, sMinute, 0);
                               long timeInMillis = calendar.getTimeInMillis();
                               java.text.DateFormat dateFormatter = new SimpleDateFormat("h:mm:ss aa");
                               Date date_c = new Date();
                               date_c.setTime(timeInMillis);
                               calendar.setTime(date_c);

                               SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss aa");
                               out_time_request = sdf.format(calendar.getTime());

                               tv_end_time.setText(out_time_request);

                               Log.e("selected_time",""+calendar.getTime());

                           }
                       }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
               picker_end.show();
               picker_end.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_200));
               picker_end.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purple_200));

               break;

           case R.id.tv_start_time:

               TimePickerDialog picker_star_time = new TimePickerDialog(getContext(),
                       new TimePickerDialog.OnTimeSetListener(){
                           @Override
                           public void onTimeSet(TimePicker tp, int sHour, int sMinute){

                               calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), sHour, sMinute, 0);
                               long timeInMillis = calendar.getTimeInMillis();
                               java.text.DateFormat dateFormatter = new SimpleDateFormat("h:mm:ss aa");
                               Date date_c = new Date();
                               date_c.setTime(timeInMillis);
                               calendar.setTime(date_c);

                               SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss aa");
                               in_time_request = sdf.format(calendar.getTime());

                               tv_start_time.setText(out_time_request);

                               Log.e("selected_time",""+calendar.getTime());

                           }
                       }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
               picker_star_time.show();
               picker_star_time.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_200));
               picker_star_time.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purple_200));


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
