package com.organic.india.ui.activity.attendance_report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.organic.india.R;
import com.organic.india.pojo.team_listing.Employee;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.fragments.attendance_report.Attendance_report;
import com.organic.india.ui.fragments.employee_attendance_report.Employee_attendance_report;

import java.util.List;

public class Attendance_report_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);

        loadFragment(Organic_india.getInstance().getMe().getIrm().equals("manager")?new Employee_attendance_report(new Employee_attendance_report.Team_player() {
            @Override
            public void selected_player(Employee employee, List<Employee> employees,int pos) {

                loadFragment(new Attendance_report(""+employee.getEmployeeId(),employee.getEmployeeCode(),true,employees,pos));
            }
        }):new Attendance_report(""+Organic_india.getInstance().getMe().getEmployeeId(),Organic_india.getInstance().getMe().getEmployeeCode(),false));
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