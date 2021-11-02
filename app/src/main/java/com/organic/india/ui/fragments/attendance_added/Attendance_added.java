package com.organic.india.ui.fragments.attendance_added;

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
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.common.Constant;
import com.organic.india.common.Functions_common;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.attendance.Attendance;
import com.organic.india.singletone.Organic_india;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Attendance_added extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    //params
    String employee_id= ""+Organic_india.getInstance().getMe().getEmployeeId();
    String employee_code=""+Organic_india.getInstance().getMe().getEmployeeCode();
    String attendance_id="";
    String check_in="";
    String check_out="";
    String attendance_date="";
    String out_time_ip="";
    String out_time_lat="";
    String out_time_long="";


    //location
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;


    Functions_common functions_common;
    Calendar calendar = Calendar.getInstance();
    Calendar runtime_calendar = Calendar.getInstance();
    CountDownTimer cdt;

    List<String> permissions = new ArrayList<>();

    Unbinder unbinder;
    View view;

    @BindView(R.id.tv_index)TextView tv_index;
    @BindView(R.id.tv_employeename)TextView tv_employeename;
    @BindView(R.id.tv_in_time)TextView tv_in_time;
    @BindView(R.id.tv_out_time)TextView tv_out_time;
    @BindView(R.id.tv_proceed)TextView tv_proceed;

    boolean did_checked_out=false;

    Attendance attendance;
    Attendance_already_added_actions attendance_already_added_actions;

    public Attendance_added(Attendance attendance, Attendance_already_added_actions attendance_already_added_actions) {
        this.attendance = attendance;
        this.attendance_already_added_actions = attendance_already_added_actions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

       view =   inflater.inflate(R.layout.fragment_attendance_added, container, false);
       unbinder= ButterKnife.bind(this,view);

       if (attendance!=null){

           tv_index.setText(""+attendance.getData().getAttendanceStatus());
           tv_employeename.setText(Organic_india.getInstance().getMe().getName());
           tv_in_time.setText(attendance.getData().getCheckIn()!=null?""+attendance.getData().getCheckIn():"-");
           tv_out_time.setText(attendance.getData().getCheckOut()!=null?""+attendance.getData().getCheckOut():"-");

           attendance_id=""+attendance.getAttandanceId();
           check_in = attendance.getData().getCheckIn();
           check_out = attendance.getData().getCheckOut()!=null?attendance.getData().getCheckOut():"";
           attendance_date=attendance.getData().getAttendanceDate();
           out_time_ip=functions_common.getLocalIpAddress();

           did_checked_out= attendance.getData().getCheckOut()!=null;

       }
       else{

           Log.e("nddjm",""+SharedPreferenceUtils.getString(Constant.yyyy_mm_dd(Calendar.getInstance()),SharedPreferenceUtils.User_Creds.user_creds));

           try{

               JSONObject jsonObject = new JSONObject(SharedPreferenceUtils.getString(Constant.yyyy_mm_dd(Calendar.getInstance()),SharedPreferenceUtils.User_Creds.user_creds));
               tv_employeename.setText(Organic_india.getInstance().getMe().getName());
               tv_index.setText(""+jsonObject.getInt("attendance_status"));
               tv_out_time.setText(jsonObject.has("check_out")?""+jsonObject.getString("check_out"):"-");
               tv_in_time.setText(jsonObject.has("check_in")?""+jsonObject.getString("check_in"):"-");

               attendance_id=""+jsonObject.getString("id");
               check_in = jsonObject.has("check_in")?""+jsonObject.getString("check_in"):"";
               check_out = jsonObject.has("check_out")?""+jsonObject.getString("check_out"):"";
               attendance_date = jsonObject.has("attendance_date")?""+jsonObject.getString("attendance_date"):"";

               out_time_ip=functions_common.getLocalIpAddress();

               did_checked_out= jsonObject.has("check_out");


           }catch (JSONException e){
               e.printStackTrace();
           }
       }

       if (!did_checked_out){
           countDownStart(calendar.getTimeInMillis()+60000);
       }


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


        tv_proceed.setOnClickListener(this::onClick);

       return view;
    }



    @Override
    public void onClick(View view) {
      switch (view.getId()){

          case R.id.tv_proceed:
              make_checkout();
              break;
      }
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        if (cdt!=null){
            cdt.cancel();
        }
        unbinder.unbind();

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


    void make_checkout(){

        if (out_time_long.isEmpty() || out_time_lat.isEmpty()){
            functions_common.toast("Location not found");
            return;
        }

        if (cdt!=null){

            cdt.cancel();
        }

        functions_common.show_loader("Requesting for CheckOut");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", employee_id);
        jsonObject.addProperty("employee_code",employee_code);
        jsonObject.addProperty("attendance_id",attendance_id);
        jsonObject.addProperty("check_in",check_in);
        jsonObject.addProperty("check_out",check_out.isEmpty()?Constant.hh_mm_ss_ampm(runtime_calendar):check_out);
        jsonObject.addProperty("attendance_date",attendance_date.isEmpty()?Constant.yyyy_mm_dd(runtime_calendar):attendance_date);
        jsonObject.addProperty("out_time_ip",out_time_ip);
        jsonObject.addProperty("out_time_lat",out_time_lat);
        jsonObject.addProperty("out_time_long",out_time_long);

        Log.e("update_attandance","PARAMS ===> "+jsonObject.toString());


        Api_instence.getRetrofitInstance().update_attandance(jsonObject).enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String res_update_attandance="";
                        JSONObject res_object;
                        if (response.isSuccessful()){
                            try {
                                res_update_attandance=response.body().string();
                                res_object=new JSONObject(res_update_attandance);
                                switch (res_object.getInt("status")){

                                    case 200:
                                        attendance_checkedout("CheckedOut successfully");
                                        break;

                                    default:
                                        attendance_checkedout(res_object.getString("message"));
                                        break;

                                }

                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            res_update_attandance="not successful "+response.message();
                        }
                        Log.e("update_attandance",res_update_attandance);

                        functions_common.dismiss_loader();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("update_attandance","failed "+t.getMessage());
                        functions_common.fail_request();
                        functions_common.dismiss_loader();
                    }
                }
        );
    }



    private void attendance_checkedout(String message){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                    public void onClick(final DialogInterface dialog, final int id) {

                        attendance_already_added_actions.checkedout();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    public void countDownStart(long total_millis){

          cdt = new CountDownTimer(total_millis, 1000){
            @Override
            public void onTick(long millisUntilFinished){

                runtime_calendar.setTimeInMillis(millisUntilFinished);
                tv_out_time.setText(Constant.yyyy_mm_dd_hh_mm_ss_ampm(runtime_calendar));

            }
            @Override
            public void onFinish(){
                countDownStart(total_millis+60000);
            }
        };
        cdt.start();
    }

    public interface Attendance_already_added_actions{
        void checkedout();
    }
}