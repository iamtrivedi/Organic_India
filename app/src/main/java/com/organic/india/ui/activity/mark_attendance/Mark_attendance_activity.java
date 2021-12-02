package com.organic.india.ui.activity.mark_attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.organic.india.R;
import com.organic.india.common.Constant;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.pojo.attendance.Attendance;
import com.organic.india.ui.activity.attendance_added.Attendance_added_activity;
import com.organic.india.ui.fragments.mark_attendance.Mark_attendance;
import java.io.Serializable;
import java.util.Calendar;

public class Mark_attendance_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        if (SharedPreferenceUtils.prefExists(Constant.yyyy_mm_dd(Calendar.getInstance()))) {

            startActivity(new Intent(Mark_attendance_activity.this, Attendance_added_activity.class)
                    .putExtra("Attendance_added",(Serializable)(Attendance) null));
            finish();

        } else {

            loadFragment(new Mark_attendance(new Mark_attendance.Attendance_actions() {
                @Override
                public void already_added(Attendance attendance) {

                    startActivity(new Intent(Mark_attendance_activity.this, Attendance_added_activity.class)
                            .putExtra("Attendance_added",(Serializable)attendance));
                    finish();

                }

                @Override
                public void canceled_permission() {
                    Toast.makeText(Mark_attendance_activity.this, "please enable GPS", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                @Override
                public void attendance_added() {
                    onBackPressed();
                }
            }));
        }
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