package com.organic.india.ui.fragments.mark_attendance;

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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.common.Constant;
import com.organic.india.common.Functions_common;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.data.Api_instence;
import com.organic.india.singletone.Organic_india;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mark_attendance extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    //params
    String employee_id= ""+Organic_india.getInstance().getMe().getEmployeeId();
    String employee_code=""+Organic_india.getInstance().getMe().getEmployeeCode();
    String check_in="";
    String attendance_date="";
    String in_time_ip="";
    String in_time_lat="";
    String in_time_long="";

    Functions_common functions_common;
    Calendar calendar = Calendar.getInstance();

    //location
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    List<String> permissions = new ArrayList<>();


    View view;
    Unbinder unbinder;
    @BindView(R.id.tv_date)TextView tv_date;
    @BindView(R.id.tv_mark_attendance) TextView tv_mark_attendance;

    Attendance_actions attendance;

    public Mark_attendance(Attendance_actions attendance) {
        this.attendance = attendance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mark_attendance, container, false);
        unbinder = ButterKnife.bind(this, view);

        functions_common=new Functions_common(getActivity(), new Functions_common.Permission_handle() {
            @Override
            public void granted() {
                statusCheck();
            }

            @Override
            public void denied() {
             attendance.canceled_permission();
            }
        });

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        functions_common.handle_permission(getActivity(),permissions);


        tv_date.setText(Constant.yyyy_mm_dd(calendar));

        attendance_date=Constant.yyyy_mm_dd(calendar);
        in_time_ip=functions_common.getLocalIpAddress();
        check_in = Constant.hh_mm_ss_ampm(calendar);


        tv_mark_attendance.setOnClickListener(this::onClick);

        return view;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){

            case R.id.tv_mark_attendance:
                make_attendance();
                break;
        }
    }

    void make_attendance(){

       if (in_time_long.isEmpty() || in_time_lat.isEmpty()){
           functions_common.toast("Location not found");
           return;
       }

        functions_common.show_loader("Requesting for fill attendance");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", employee_id);
        jsonObject.addProperty("employee_code",employee_code);
        jsonObject.addProperty("check_in",check_in);
        jsonObject.addProperty("attendance_date",attendance_date);
        jsonObject.addProperty("in_time_ip",in_time_ip);
        jsonObject.addProperty("in_time_lat",in_time_lat);
        jsonObject.addProperty("in_time_long",in_time_long);

        Log.e("add_attandance","PARAMS===>"+jsonObject.toString());

        Api_instence.getRetrofitInstance().add_attandance(jsonObject).enqueue(new Callback<com.organic.india.pojo.attendance.Attendance>() {
            @Override
            public void onResponse(Call<com.organic.india.pojo.attendance.Attendance> call, Response<com.organic.india.pojo.attendance.Attendance> response) {
                if (response.isSuccessful()){

                    switch (response.body().getStatus()){

                        case 200:

                            calendar.set(Calendar.DATE,-1);
                            SharedPreferenceUtils.removeSavedPref(Constant.yyyy_mm_dd(calendar));
                            SharedPreferenceUtils.putString(SharedPreferenceUtils.User_Creds.attendance_metadata,new Gson().toJson(response.body().getData()));
                            attendance_added();

                            break;

                        case 202:

                            Log.e("add_attandance",""+new Gson().toJson(response.body()));

                            SharedPreferenceUtils.putString(SharedPreferenceUtils.User_Creds.attendance_metadata,new Gson().toJson(response.body().getData()));
                            functions_common.toast(response.body().getMessage());
                            attendance.already_added(response.body());

                            break;

                        default:
                            functions_common.toast(response.body().getMessage());
                            break;
                    }

                }else{
                    functions_common.toast("something went wrong");
                }

                Log.e("add_attandance",""+new Gson().toJson(response.body()));
                functions_common.dismiss_loader();
            }

            @Override
            public void onFailure(Call<com.organic.india.pojo.attendance.Attendance> call, Throwable t) {
                functions_common.dismiss_loader();
                functions_common.fail_request();
            }
        });
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

        } else {
             Toast.makeText(getActivity(), "Reload Again ! Location not Detected", Toast.LENGTH_LONG).show();
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


    private void attendance_added(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Attendance marked successfully !")
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                    public void onClick(final DialogInterface dialog, final int id){
                       attendance.attendance_added();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public interface Attendance_actions{
        void already_added(com.organic.india.pojo.attendance.Attendance attendance);
        void canceled_permission();
        void attendance_added();
    }
}