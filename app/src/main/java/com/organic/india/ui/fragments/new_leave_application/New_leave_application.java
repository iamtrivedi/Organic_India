package com.organic.india.ui.fragments.new_leave_application;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Leave_chart_adapter;
import com.organic.india.adapter.Leave_type_spinner_adapter;
import com.organic.india.common.Constant;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_Interfaces;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.attendance_log.Attendance_log;
import com.organic.india.pojo.pending_leave.Data;
import com.organic.india.pojo.pending_leave.Pending_leave;
import com.organic.india.pojo.team_listing.Team_listing;
import com.organic.india.providers.FileUtils;
import com.organic.india.singletone.Organic_india;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class New_leave_application extends Fragment {

    //params
    String employee_id=""+Organic_india.getInstance().getMe().getEmployeeId();
    String employee_code=""+Organic_india.getInstance().getMe().getEmployeeCode();
    String leave_category_id="";
    String start_date="";
    String end_date="";
    String optional_leave="";
    Uri medical_certificate=null;
    String reason="";
    int day_difference=0;

    Functions_common functions_common;

    List<Data> chart_data=new ArrayList<>();
    List<Data> available_leaves=new ArrayList<>();
    Leave_chart_adapter adapter;
    Leave_type_spinner_adapter leave_type_spinner_adapter;
    Calendar calendar = Calendar.getInstance();

    Calendar cal_start_date= Calendar.getInstance();
    Calendar cal_end_date= Calendar.getInstance();

    List<String> permissions = new ArrayList<>();

    static final int ChooseImage = 654;

    @BindView(R.id.leave_spinner)Spinner leave_spinner;
    @BindView(R.id.tv_add_leave)TextView tv_add_leave;
    @BindView(R.id.tv_cancel)TextView tv_cancel;
    @BindView(R.id.tv_start_date)TextView tv_start_date;
    @BindView(R.id.tv_end_date)TextView tv_end_date;
    @BindView(R.id.et_reason)EditText et_reason;
    @BindView(R.id.iv_prescription)ImageView iv_prescription;
    @BindView(R.id.ll_upload_file)LinearLayout ll_upload_file;
    @BindView(R.id.ll_leave_chart)LinearLayout ll_leave_chart;

    @BindView(R.id.ll_startdate) RelativeLayout ll_startdate;
    @BindView(R.id.ll_enddate)RelativeLayout ll_enddate;

    @BindView(R.id.rcy_chip)RecyclerView rcy_chip;

    View view;

    Leave_actions leave_actions;

    public New_leave_application(Leave_actions leave_actions) {
        this.leave_actions = leave_actions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.fragment_new_leave_application, container, false);
        ButterKnife.bind(this,view);


        functions_common=new Functions_common(getActivity(), new Functions_common.Permission_handle() {
            @Override
            public void granted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Add medical prescription"), ChooseImage);

            }
            @Override
            public void denied() {

            }
        });
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        get_leave_chart();


        leave_type_spinner_adapter = new Leave_type_spinner_adapter(getContext(), available_leaves);
        leave_spinner.setAdapter(leave_type_spinner_adapter);
        leave_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){

                ll_upload_file.setVisibility(available_leaves.get(i).getLeaveCategory().equals("Medical Leave (ML)")?View.VISIBLE:View.GONE);
                leave_category_id=""+available_leaves.get(i).getLeaveCategoryId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ll_enddate.setOnClickListener(v->{
            end_date();
        });

        ll_startdate.setOnClickListener(v->{
         start_date();
        });

        iv_prescription.setOnClickListener(v->{
            functions_common.handle_permission(getActivity(),permissions);
        });

        tv_add_leave.setOnClickListener(v->{
            add_leave_request();
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
          if (requestCode == ChooseImage) {
                if (data == null) {
                    return;
                }
                medical_certificate = data.getData();
                iv_prescription.setImageURI(medical_certificate);
            }
        }
    }


    void add_leave_request(){

     if (validated()){

         functions_common.show_loader("Requesting Leave Application");

         Map<String, RequestBody> map = new HashMap<>();
         map.put("employee_id", RequestBody.create(MediaType.parse("text/plain"), employee_id));
         map.put("employee_code", RequestBody.create(MediaType.parse("text/plain"), employee_code));
         map.put("leave_category_id", RequestBody.create(MediaType.parse("text/plain"), leave_category_id));
         map.put("start_date", RequestBody.create(MediaType.parse("text/plain"), start_date));
         map.put("end_date", RequestBody.create(MediaType.parse("text/plain"), end_date));
         map.put("optional_leave", RequestBody.create(MediaType.parse("text/plain"), optional_leave));
         map.put("reason", RequestBody.create(MediaType.parse("text/plain"), reason));
         MultipartBody.Part prescription_part=null;

         if(medical_certificate!=null && day_difference>2){

             File file = new File(FileUtils.getPath(getContext(), medical_certificate));
             prescription_part = MultipartBody.Part.createFormData("medical_certificate", file.getName(), RequestBody.create(MediaType.parse(FileUtils.getMimeType(getContext(), medical_certificate)), file));

         }

         Api_instence.getRetrofitInstance().create_leave(map,prescription_part)
                 .enqueue(new Callback<ResponseBody>() {
                     @Override
                     public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                         if (response.isSuccessful()){
                             try {
                                 String create_leave = response.body().string();
                                 JSONObject object = new JSONObject(create_leave);
                                 switch (object.getInt("status")){

                                     case 200:
                                         functions_common.dismiss_loader();
                                         functions_common.toast(""+object.getString("message"));
                                         leave_actions.on_leave_request_added();
                                         break;

                                     default:
                                         functions_common.dismiss_loader();
                                         functions_common.toast(""+object.getString("message"));
                                         break;
                                 }
                                 Log.e("create_leave",""+create_leave);

                             } catch (IOException | JSONException e) {
                                 e.printStackTrace();
                                 functions_common.dismiss_loader();
                                 Log.e("create_leave","json "+e.getMessage());
                             }
                         }else{
                             functions_common.dismiss_loader();
                             Log.e("create_leave","json "+response.message());
                             functions_common.toast("something went wrong");
                         }
                     }
                     @Override
                     public void onFailure(Call<ResponseBody> call, Throwable t) {
                         functions_common.toast("sorry ! couldn't make a request");
                         functions_common.dismiss_loader();
                     }
                 });
        }

    }


    boolean validated(){

        reason=et_reason.getText().toString();

        if (leave_category_id.isEmpty()){
            functions_common.toast("please select leave type");
            return false;
        }else if (start_date.isEmpty()){
            functions_common.toast("please select starting date");
            return false;
        }else if (end_date.isEmpty()){
            functions_common.toast("Please select ending date");
            return false;
        }else if(leave_category_id.equals("1") && medical_certificate==null && day_difference>2){
            functions_common.toast("Medical prescription is require for medical leave");
            return false;
        }else if (reason.isEmpty()){
            functions_common.toast("Please add the reason");
            return false;
        }

        return true;
    }


    void get_leave_chart(){

        adapter = new Leave_chart_adapter(chart_data,getContext());
        rcy_chip.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        rcy_chip.setAdapter(adapter);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());

        Api_instence.getRetrofitInstance().pending_leave(jsonObject)
                .enqueue(new Callback<Pending_leave>() {
                    @Override
                    public void onResponse(Call<Pending_leave> call, Response<Pending_leave> response){
                        chart_data.addAll(response.body().getData()!=null?response.body().getData():chart_data);
                        ll_leave_chart.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();

                        for (Data chart : chart_data){
                            if (!chart.getPendingLeave().equals("0.00") && !chart.getPendingLeave().isEmpty()){
                                available_leaves.add(chart);
                            }
                        }
                        leave_type_spinner_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Pending_leave> call, Throwable t) {
                        ll_leave_chart.setVisibility(View.GONE);
                    }
                });
    }


    public void start_date(){

        DatePickerDialog datePickerDialog= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth){
                Calendar temp = Calendar.getInstance();
                temp.set(Calendar.YEAR, year);
                temp.set(Calendar.MONTH, monthOfYear);
                temp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal_start_date.setTime(temp.getTime());

                tv_start_date.setText(Constant.formated_date(cal_start_date));
                start_date=Constant.yyyy_mm_dd(cal_start_date);

            }
        },cal_start_date.get(Calendar.YEAR), cal_start_date.get(Calendar.MONTH),cal_start_date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_200));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purple_200));

    }


    public void end_date(){

        if (start_date.isEmpty()){
            Toast.makeText(getContext(), "please add start date", Toast.LENGTH_SHORT).show();
            return;
        }else{

            DatePickerDialog datePickerDialog= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth){
                    Calendar temp = Calendar.getInstance();
                    temp.set(Calendar.YEAR, year);
                    temp.set(Calendar.MONTH, monthOfYear);
                    temp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    cal_end_date.setTime(temp.getTime());

                    long msDiff = cal_end_date.getTimeInMillis() - cal_start_date.getTimeInMillis();
                    day_difference = (int) TimeUnit.MILLISECONDS.toDays(msDiff);

                    if(day_difference<0){

                        functions_common.toast("invalid date");

                    }else{
                        tv_end_date.setText(Constant.formated_date(cal_end_date));
                        end_date=Constant.yyyy_mm_dd(cal_end_date);
                    }
                }
            },cal_end_date.get(Calendar.YEAR), cal_end_date.get(Calendar.MONTH),cal_end_date.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_200));
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purple_200));

        }

    }

    public interface Leave_actions{
        void on_leave_request_added();
    }
}