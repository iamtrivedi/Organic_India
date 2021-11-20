package com.organic.india.ui.fragments.attendance_report;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.attendance_calender.CalendarView;
import com.organic.attendance_calender.EventDay;
import com.organic.attendance_calender.exceptions.OutOfDateRangeException;
import com.organic.attendance_calender.listeners.OnCalendarPageChangeListener;
import com.organic.attendance_calender.listeners.OnDayClickListener;
import com.organic.india.R;
import com.organic.india.bottom_sheet.Request_add_atendance;
import com.organic.india.bottom_sheet.View_attendance_log_sheet;
import com.organic.india.common.Constant;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.attendance_report.AttendanceReport;
import com.organic.india.pojo.attendance_report.Attendance_report_res;
import com.organic.india.pojo.update_attendance.Update_attendance_res;
import com.organic.india.singletone.Organic_india;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Attendance_report extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    //location
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    String in_time_lat="";
    String in_time_long="";

    String[] items={};

    Functions_common functions_common;

    List<EventDay> events = new ArrayList<>();
    List<AttendanceReport> attendanceReport= new ArrayList<>();
    List<String> permissions = new ArrayList<>();

    Calendar current_date = Calendar.getInstance();

    @BindView(R.id.attendance_calendar)CalendarView attendance_calendar;
    @BindView(R.id.pb_load_calendar)ProgressBar pb_load_calendar;

    View view;

    String employee_id,employee_code;
    boolean is_manager=false;

    public Attendance_report(String employee_id, String employee_code, boolean is_manager) {
        this.employee_id = employee_id;
        this.employee_code = employee_code;
        this.is_manager = is_manager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

        view= inflater.inflate(R.layout.fragment_attendance_report, container, false);
        ButterKnife.bind(this,view);
        functions_common=new Functions_common(getContext());


        get_attendance_report(attendance_calendar.getCurrentPageDate().getTime());


        attendance_calendar.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener(){
            @Override
            public void onChange(){
                get_attendance_report(attendance_calendar.getCurrentPageDate().getTime());
            }

            @Override
            public void onChange_of_month() {

                choose_year_month();

            }
        });



        attendance_calendar.setOnForwardPageChangeListener(new OnCalendarPageChangeListener(){
            @Override
            public void onChange(){

                get_attendance_report(attendance_calendar.getCurrentPageDate().getTime());

            }
            @Override
            public void onChange_of_month() {

                choose_year_month();
            }
        });


        attendance_calendar.setOnDayClickListener(new OnDayClickListener(){
            @Override
            public void onDayClick(EventDay eventDay){

                if (eventDay.getCalendar().getTimeInMillis()<=current_date.getTimeInMillis() && events.size()>0){
                    select_operation_on_date(Constant.yyyy_mm_dd(eventDay.getCalendar()),(AttendanceReport) eventDay.getData());
                }

//                if (!is_manager){
//                    if (eventDay.getCalendar().getTimeInMillis()<=current_date.getTimeInMillis() && events.size()>0){
//                        select_operation_on_date(Constant.yyyy_mm_dd(eventDay.getCalendar()));
//                    }
//                }
            }
        });


        functions_common=new Functions_common(getActivity(), new Functions_common.Permission_handle(){
            @Override
            public void granted(){

                boolean is_manager_account = is_manager && TextUtils.equals(employee_code,Organic_india.getInstance().getMe().getEmployeeCode()) && TextUtils.equals(employee_id,""+Organic_india.getInstance().getMe().getEmployeeId());

                if(is_manager_account || !is_manager){
                    statusCheck();
                }
            }
            @Override
            public void denied() {
                functions_common.toast("please enable location");
            }
        });

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        functions_common.handle_permission(getActivity(),permissions);

        return view;
    }

    void get_attendance_report(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        pb_load_calendar.setVisibility(View.VISIBLE);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", employee_id);
        jsonObject.addProperty("employee_code",employee_code);
        jsonObject.addProperty("is_login",1);
        jsonObject.addProperty("month_year", Constant.yyyy_mm(calendar));

        Log.e("pass_param",jsonObject.toString());

        Api_instence.getRetrofitInstance().attendance_report(jsonObject)
                .enqueue(new Callback<Attendance_report_res>() {
                    @Override
                    public void onResponse(Call<Attendance_report_res> call, Response<Attendance_report_res> response) {
                       if (response.isSuccessful()){
                           switch (response.body().getStatus()){

                               case 200:
                                   arrange_attendance(response.body().getAttendanceReport());
                                   break;

                               default:
                                   functions_common.toast(response.body().getMessage());
                                   pb_load_calendar.setVisibility(View.GONE);
                                   break;
                           }

                       }else{
                           functions_common.toast(response.message());
                           pb_load_calendar.setVisibility(View.GONE);
                       }

                       Log.e("response_str",new Gson().toJson(response.body()));
                    }
                    @Override
                    public void onFailure(Call<Attendance_report_res> call, Throwable t) {
                       functions_common.toast("couldn't connect with server");
                        pb_load_calendar.setVisibility(View.GONE);
                    }
                });
    }


    void arrange_attendance(List<AttendanceReport> attendanceReport){

        events.removeAll(events);
        attendance_calendar.setEvents(events);
        this.attendanceReport.removeAll(this.attendanceReport);
        this.attendanceReport.addAll(attendanceReport);

        for (AttendanceReport report : this.attendanceReport){

            try {


                String data_report = report.getPresent()!=null?report.getPresent():"";
                if (!data_report.isEmpty() && report.getLog().equals("Yes")){
                    events.add(new EventDay(Constant.yyyy_mm_dd_str_to_date(report.getDate()),R.drawable.cal_present,report));
                    continue;
                }

                if (!report.getWeeklyOff().isEmpty()){
                    events.add(new EventDay(Constant.yyyy_mm_dd_str_to_date(report.getDate()),R.drawable.cal_weeknd,report));
                    continue;
                }

                if (!report.getStatus().isEmpty() && report.getStatus().equals("reject")){
                    events.add(new EventDay(Constant.yyyy_mm_dd_str_to_date(report.getDate()),R.drawable.cal_reject,report));
                    continue;
                }

                if (!report.getHoliday().isEmpty() && report.getHoliday().equals("H")){
                    events.add(new EventDay(Constant.yyyy_mm_dd_str_to_date(report.getDate()),R.drawable.cal_holiday,report));
                    continue;
                }


                String data_weekly_off = report.getWeeklyOff()!=null?report.getWeeklyOff():"";
                String data_holiday = report.getHoliday()!=null?report.getHoliday():"";
                String data_present = report.getPresent()!=null?report.getPresent():"";
                String data_send = report.getStatus()!=null?report.getStatus():"";
                if (data_weekly_off.isEmpty() && data_holiday.isEmpty() && data_present.isEmpty() && data_send.equals("send")){
                    events.add(new EventDay(Constant.yyyy_mm_dd_str_to_date(report.getDate()),R.drawable.cal_no_presence,report));
                    continue;
                }

            }catch (NullPointerException e){
                continue;
            }

        }
        attendance_calendar.setEvents(events);
        pb_load_calendar.setVisibility(View.GONE);
    }


    void updateattendance(JsonObject jsonObject){

        Log.e("attandance_param",""+jsonObject.toString());

        functions_common.show_loader("Updating attendance");

        Api_instence.getRetrofitInstance().attandance_request(jsonObject)
                .enqueue(new Callback<Update_attendance_res>() {
                    @Override
                    public void onResponse(Call<Update_attendance_res> call, Response<Update_attendance_res> response) {
                        switch (response.body().getStatus()){

                            case 200:
                                functions_common.toast(response.body().getMessage());
                                get_attendance_report(attendance_calendar.getCurrentPageDate().getTime());
                                break;

                            default:
                                functions_common.toast(response.body().getMessage());
                                functions_common.dismiss_loader();
                                break;

                        }
                    }
                    @Override
                    public void onFailure(Call<Update_attendance_res> call, Throwable t) {
                        functions_common.toast("couldn't make request");
                        functions_common.dismiss_loader();
                    }
                });
    }


    void valid_date_edit(String date){


    }


    void request_add_attendance(String date,AttendanceReport report){

        if (in_time_lat.isEmpty() || in_time_long.isEmpty()){

          functions_common.handle_permission(getActivity(),permissions);

        }else{
            new Request_add_atendance(date,report,new Request_add_atendance.Action() {
                @Override
                public void update_attendance(JsonObject jsonObject) {
                    jsonObject.addProperty("in_time_lat", in_time_lat);
                    jsonObject.addProperty("in_time_long", in_time_long);
                    updateattendance(jsonObject);
                }
            }).show(getChildFragmentManager(),"Request_add_atendance");
        }
    }


    @Override
    public void onConnected(Bundle bundle){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){

            startLocationUpdates();

        }
        if (mLocation != null) {

            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();
            in_time_lat=""+latitude;
            in_time_long=""+longitude;

            Toast.makeText(getActivity(), "Location Detected", Toast.LENGTH_SHORT).show();

            Log.e("detected_location",""+latitude+" "+longitude);

        } else {
            connect_gps();
        }
    }

    protected void startLocationUpdates(){

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(500);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("mark_attendance", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("mark_attendance", "Connection failed. Error: " + connectionResult.getErrorMessage());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        final LocationManager manager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if(mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }
    @Override

    public void onLocationChanged(Location location){

    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps(){

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            functions_common.show_loader("getting current location...");
            connect_gps();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Please enable GPS to fill attendance")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {

                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener(){
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            functions_common.show_loader("getting current location...");
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){

                    connect_gps();

                }
            },1000);
        }else{

        }
    }

    void connect_gps(){
        functions_common.dismiss_loader();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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

                Calendar calendar= Calendar.getInstance();
                calendar.set(year,monthOfYear+1,0);

                try {
                    attendance_calendar.setDate(calendar);
                } catch (OutOfDateRangeException e){
                    e.printStackTrace();
                }

               get_attendance_report(attendance_calendar.getCurrentPageDate().getTime());

            }
        });

        dialogFragment.show(getChildFragmentManager(), null);
    }

    void select_operation_on_date(String date,AttendanceReport report){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Please select");

        boolean is_manager_account = is_manager && TextUtils.equals(employee_code,Organic_india.getInstance().getMe().getEmployeeCode()) && TextUtils.equals(employee_id,""+Organic_india.getInstance().getMe().getEmployeeId());

        if (is_manager_account || !is_manager){
            items = new String[]{"Regularize Attendance", "View Attendance Log"};
        }else{
            items = new String[]{"View Attendance Log"};
        }

        int checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){

                        if (items[which].equals("View Attendance Log")) {
                            apply_date_via(date, true,report);
                        } else {
                            apply_date_via(date, false,report);
                        }

                        valid_date_edit(date);
                        dialog.dismiss();
                    }
                },500);
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    void apply_date_via(String date,boolean view_log,AttendanceReport report){

        if (view_log){
            new View_attendance_log_sheet(date).show(getFragmentManager(),"View_attendance_log_sheet");
        }else{
            request_add_attendance(date,report);
//            for (AttendanceReport report : attendanceReport){
//
//                if (report.getDate().equals(date) && report.getWeeklyOff().isEmpty() && report.getHoliday().isEmpty() && report.getPresent().isEmpty() && report.getStatus().equals("send")){
//                    request_add_attendance(date);
//                    break;
//                }else{
//                    Log.e("get_date",""+report.getDate()+" "+date);
//                }
//            }
        }
    }
}