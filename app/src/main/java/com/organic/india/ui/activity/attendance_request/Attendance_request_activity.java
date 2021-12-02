package com.organic.india.ui.activity.attendance_request;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.organic.india.R;
import com.organic.india.ui.fragments.attendance_request.Attendance_request;

public class Attendance_request_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_request);

        loadFragment(new Attendance_request());
    }


    private void loadFragment(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, fragment, fragmentTag);
            transaction.commit();
        }
    }
}