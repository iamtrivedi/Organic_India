package com.organic.india.ui.activites.attendance_location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.organic.india.R;
import com.organic.india.common.Location_permit;

public class Attendance_location extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    boolean hasLocation = false;
    Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_location);

        hasLocation=getIntent().hasExtra("LAT_LNG");
        if (hasLocation){
            lat=getIntent().getDoubleExtra("LAT",00);
            lng=getIntent().getDoubleExtra("LNG",00);
        }

        buildGoogleApiClient();
        initilizeMap();
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
    }

    private void initilizeMap() {
        if (googleMap == null) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        }
    }

    private void connectToGoogleClient(){
        if (!Location_permit.isGpsEnabled(Attendance_location.this)) {
            Location_permit.requestForDirectGps(Attendance_location.this);
        } else {
            if (!hasLocation) {
                if (googleApiClient != null) {
                    googleApiClient.connect();
                }
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        this.googleMap = googleMap;
        if (hasLocation) {
            LatLng currentPosition = new LatLng(lat, lng);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));
        } else {
            if (Location_permit.isLocationPermissionAvailable(Attendance_location.this)) {
                connectToGoogleClient();
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Location_permit.requestForDirectGps(Attendance_location.this);
                return;
            }
            this.googleMap.setMyLocationEnabled(true);
        }
        this.googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng midLatLng = googleMap.getCameraPosition().target;
                lat = midLatLng.latitude;
                lng = midLatLng.longitude;
                updateLat_lng(midLatLng.latitude,midLatLng.longitude);

            }
        });
    }

    private void updateLat_lng(double u_lat, double u_lng){
        lat=u_lat;
        lng=u_lng;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Location_permit.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    connectToGoogleClient();
                } else {
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Location_permit.LOCATION_SETTINGS_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        connectToGoogleClient();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (!Location_permit.checkPlayServices(this)){
            Toast.makeText(this, "You need to install Google Play Services to use the App properly", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null && googleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }
}