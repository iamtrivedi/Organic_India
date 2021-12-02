package com.organic.india.ui.fragments.attendance_request;

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
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Attendance_request_adapter;
import com.organic.india.common.Constant;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.dialog.Accept_or_Reject_attendance;
import com.organic.india.pojo.attendance_request.Request;
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

public class Attendance_request extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    //location
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    Attendance_request_adapter adapter;
    private List<Request> requestList = new ArrayList<>();

    List<String> permissions = new ArrayList<>();


    Functions_common functions_common;
    Calendar current_date = Calendar.getInstance();

    String out_time_ip="";
    String out_time_lat="";
    String out_time_long="";

    @BindView(R.id.currentDateLabel)TextView currentDateLabel;
    @BindView(R.id.previousButton)ImageView previousButton;
    @BindView(R.id.forwardButton)ImageView forwardButton;
    @BindView(R.id.rcy_attendance_report)RecyclerView rcy_attendance_report;
    @BindView(R.id.pb_progress)ProgressBar pb_progress;
    @BindView(R.id.tv_no_found)TextView tv_no_found;

    View view;

    public Attendance_request(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_attendance_request, container, false);
        ButterKnife.bind(this,view);

        functions_common=new Functions_common(getContext());

        out_time_ip=functions_common.getLocalIpAddress();

        adapter = new Attendance_request_adapter(requestList, getContext(), new Attendance_request_adapter.Actions() {
            @Override
            public void react_to_request(Request request,int position) {


                if (out_time_lat.isEmpty() || out_time_long.isEmpty()){
                    statusCheck();
                }else{
                    new Accept_or_Reject_attendance(getContext(),request, new Accept_or_Reject_attendance.React() {
                        @Override
                        public void reaction(int value) {

                            if (out_time_lat.isEmpty() || out_time_long.isEmpty()){
                                statusCheck();
                            }else{
                                approve_or_reject_attendance(request,value,position);
                            }
                        }
                    }).show();
                }

            }
        });

        rcy_attendance_report.setAdapter(adapter);

        fetch_attendance_request(current_date.getTime());

        previousButton.setOnClickListener(this::onClick);
        forwardButton.setOnClickListener(this::onClick);
        currentDateLabel.setOnClickListener(this::onClick);

        functions_common=new Functions_common(getActivity(), new Functions_common.Permission_handle() {
            @Override
            public void granted(){

                statusCheck();
            }

            @Override
            public void denied(){
                Toast.makeText(getContext(), "please enable GPS", Toast.LENGTH_SHORT).show();
            }
        });

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        functions_common.handle_permission(getActivity(),permissions);

        return view;
    }

    void fetch_attendance_request(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Format formatter = new SimpleDateFormat("MMMM yyyy");

        currentDateLabel.setText(formatter.format(date));


        pb_progress.setVisibility(View.VISIBLE);
        tv_no_found.setVisibility(View.GONE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("attendance_month", Constant.yyyy_mm(current_date));

        Log.e("params",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().get_attandance_request(jsonObject)
                .enqueue(new Callback<com.organic.india.pojo.attendance_request.Attendance_request>() {
                    @Override
                    public void onResponse(Call<com.organic.india.pojo.attendance_request.Attendance_request> call, Response<com.organic.india.pojo.attendance_request.Attendance_request> response) {
                       if (response.isSuccessful()){
                           switch (response.body().getStatus()){

                               case 200:
                                   pb_progress.setVisibility(View.GONE);
                                   requestList.removeAll(requestList);
                                   requestList.addAll(response.body().getData());
                                   adapter.notifyDataSetChanged();
                                   tv_no_found.setVisibility(requestList.size()>0?View.GONE:View.VISIBLE);
                                   break;

                               default:
                                   functions_common.toast(response.body().getMessage());
                                   tv_no_found.setVisibility(requestList.size()>0?View.GONE:View.VISIBLE);
                                   pb_progress.setVisibility(View.GONE);
                                   requestList.removeAll(requestList);
                                   adapter.notifyDataSetChanged();
                                   break;

                           }
                       }else{
                           pb_progress.setVisibility(View.GONE);
                           tv_no_found.setVisibility(requestList.size()>0?View.GONE:View.VISIBLE);
                       }
                    }
                    @Override
                    public void onFailure(Call<com.organic.india.pojo.attendance_request.Attendance_request> call, Throwable t){
                       pb_progress.setVisibility(View.GONE);
                    }
                });
    }

    void change_to_pre_month(){
        current_date.add(Calendar.MONTH, -1);
        fetch_attendance_request(current_date.getTime());

    }

    void change_to_post_month(){
        current_date.add(Calendar.MONTH, 1);
        fetch_attendance_request(current_date.getTime());
    }

    void approve_or_reject_attendance(Request request,int value,int position){

        functions_common.show_loader(value==1?"Attendance Accepting":"Attendance Rejecting");


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id",Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code",Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("attendance_id",request.getId());
        jsonObject.addProperty("in_time_request",request.getInTimeRequest());
        jsonObject.addProperty("out_time_request",request.getOutTimeRequest());
        jsonObject.addProperty("attendance_date",request.getAttendanceDate());
        jsonObject.addProperty("out_time_ip",""+out_time_ip);
        jsonObject.addProperty("out_time_lat",""+out_time_lat);
        jsonObject.addProperty("out_time_long",""+out_time_long);
        jsonObject.addProperty("reason",request.getReason());
        jsonObject.addProperty("request_status",value);

        Log.e("dddd",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().attandance_response(jsonObject)
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
                                       requestList.remove(position);
                                       adapter.notifyDataSetChanged();
                                       break;

                                   default:
                                       functions_common.toast(obj.getString("message"));
                                       break;

                               }
                               functions_common.toast(obj.getString("message"));
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
                     functions_common.toast("couldn't connect to server");
                    }
                });

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
                fetch_attendance_request(current_date.getTime());

            }
        });

        dialogFragment.show(getChildFragmentManager(), null);
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
            out_time_lat=""+latitude;
            out_time_long=""+longitude;

            Toast.makeText(getActivity(), "Location Detected", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Reload Again ! Location not Detected", Toast.LENGTH_LONG).show();
        }
    }



    protected void startLocationUpdates(){

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(500);
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
        Log.i("mark_attendance", "Connection failed. Error: " + connectionResult.getErrorCode());
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please enable GPS to Checkout")
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
}