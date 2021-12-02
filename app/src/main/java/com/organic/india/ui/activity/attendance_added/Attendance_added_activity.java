package com.organic.india.ui.activity.attendance_added;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.organic.india.R;
import com.organic.india.pojo.attendance.Attendance;
import com.organic.india.ui.fragments.attendance_added.Attendance_added;

public class Attendance_added_activity extends AppCompatActivity {

    Attendance object = new Attendance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_added);

        object= (Attendance) getIntent().getSerializableExtra("Attendance_added");

        loadFragment(new Attendance_added((Attendance) object, new Attendance_added.Attendance_already_added_actions() {
            @Override
            public void checkedout(){

                onBackPressed();
            }
        }));
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