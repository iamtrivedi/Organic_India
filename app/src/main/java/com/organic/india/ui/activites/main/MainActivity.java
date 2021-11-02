package com.organic.india.ui.activites.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.organic.india.R;
import com.organic.india.common.Constant;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.pojo.attendance.Attendance;
import com.organic.india.pojo.team_listing.Employee;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.gallery.Gallery;
import com.organic.india.ui.activites.login.Login;
import com.organic.india.ui.fragments.attendance_added.Attendance_added;
import com.organic.india.ui.fragments.attendance_report.Attendance_report;
import com.organic.india.ui.fragments.attendance_request.Attendance_request;
import com.organic.india.ui.fragments.change_password.Change_password;
import com.organic.india.ui.fragments.dashboard.Dashboard;
import com.organic.india.ui.fragments.employee_attendance_report.Employee_attendance_report;
import com.organic.india.ui.fragments.mark_attendance.Mark_attendance;
import com.organic.india.ui.fragments.my_leave_applications.My_leave_applications;
import com.organic.india.ui.fragments.new_leave_application.New_leave_application;
import com.organic.india.ui.fragments.team_leave_requests.Team_leave_request_page;
import com.organic.india.ui.fragments.user_profile.User_profile;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_drawer)ImageView iv_drawer;
    @BindView(R.id.drawer_layout)DrawerLayout drawer;
    @BindView(R.id.ll_manager_attendance_option)LinearLayout ll_manager_attendance_option;
    @BindView(R.id.ll_manager_leave_option)LinearLayout ll_manager_leave_option;

    @BindView(R.id.nav_dashboard)TextView nav_dashboard;
    @BindView(R.id.nav_add_leave_application)TextView nav_add_leave_application;
    @BindView(R.id.nav_leave_applications)TextView nav_leave_applications;
    @BindView(R.id.nav_mark_attendence)TextView nav_mark_attendence;
    @BindView(R.id.tv_attendance_report)TextView tv_attendance_report;
    @BindView(R.id.tv_request_attendance_report)TextView tv_request_attendance_report;
    @BindView(R.id.nav_changepassword)TextView nav_changepassword;
    @BindView(R.id.nav_gallery)TextView nav_gallery;
    @BindView(R.id.nav_profile)TextView nav_profile;
    @BindView(R.id.tv_logout)TextView tv_logout;

    @BindView(R.id.nav_tv_team_leave)TextView nav_tv_team_leave;
    @BindView(R.id.nav_team_leave_report)TextView nav_team_leave_report;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_drawer);
        ButterKnife.bind(this);

        load_frame(Constant.Route.Dashboard,null);

        ll_manager_leave_option.setVisibility(Organic_india.getInstance().getMe().getIrm().equals("manager")?View.VISIBLE:View.GONE);
        ll_manager_attendance_option.setVisibility(Organic_india.getInstance().getMe().getIrm().equals("manager")?View.VISIBLE:View.GONE);


        iv_drawer.setOnClickListener(this);
        nav_dashboard.setOnClickListener(this);
        nav_add_leave_application.setOnClickListener(this);
        nav_leave_applications.setOnClickListener(this);
        nav_mark_attendence.setOnClickListener(this);
        tv_attendance_report.setOnClickListener(this);
        nav_changepassword.setOnClickListener(this);
        nav_profile.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        tv_request_attendance_report.setOnClickListener(this);
        nav_tv_team_leave.setOnClickListener(this);
        nav_team_leave_report.setOnClickListener(this);
        nav_gallery.setOnClickListener(this);

    }

    private void load_frame(int route,Object object){
        switch (route) {

            case Constant.Route.Dashboard:
                loadFragment(new Dashboard());
                break;

            case Constant.Route.New_leave_application:
                loadFragment(new New_leave_application(new New_leave_application.Leave_actions() {
                    @Override
                    public void on_leave_request_added(){
                        onBackPressed();
                    }
                }));
                break;

             case Constant.Route.My_leave_applications:
                loadFragment(new My_leave_applications());
                break;

             case Constant.Route.Mark_attendance:

                 if (SharedPreferenceUtils.prefExists(Constant.yyyy_mm_dd(Calendar.getInstance()))){

                     load_frame(Constant.Route.Attendance_added,null);
                 }
                 else{

                     loadFragment(new Mark_attendance(new Mark_attendance.Attendance_actions() {
                         @Override
                         public void already_added(Attendance attendance){
                             load_frame(Constant.Route.Attendance_added,attendance);
                         }

                         @Override
                         public void canceled_permission(){
                             Toast.makeText(MainActivity.this, "please enable GPS", Toast.LENGTH_SHORT).show();
                             onBackPressed();
                         }

                         @Override
                         public void attendance_added(){
                             onBackPressed();
                         }
                     }));
                 }

                 break;

            case Constant.Route.Attendance_report:

                loadFragment(Organic_india.getInstance().getMe().getIrm().equals("manager")?new Employee_attendance_report(new Employee_attendance_report.Team_player() {
                    @Override
                    public void selected_player(Employee employee) {

                        //view attendance
                        loadFragment(new Attendance_report(""+employee.getEmployeeId(),employee.getEmployeeCode(),true));
                    }
                }):new Attendance_report(""+Organic_india.getInstance().getMe().getEmployeeId(),Organic_india.getInstance().getMe().getEmployeeCode(),false));
                break;

            case Constant.Route.Change_password:
                loadFragment(new Change_password(new Change_password.Actions() {
                    @Override
                    public void on_update() {

                    }
                    @Override
                    public void on_cancel() {
                     onBackPressed();
                    }
                }));
                break;

            case Constant.Route.Attendance_added:
                loadFragment(new Attendance_added((Attendance) object, new Attendance_added.Attendance_already_added_actions() {
                    @Override
                    public void checkedout(){

                        onBackPressed();
                    }
                }));
                break;

            case Constant.Route.User_profile:
                loadFragment(new User_profile());
                break;

            case Constant.Route.Attendance_request:
                loadFragment(new Attendance_request());
                break;

            case Constant.Route.Team_leave_request:
             //   loadFragment(new Team_leave_request_page());
                break;


            case Constant.Route.Team_leave_request_report:
                loadFragment(new Team_leave_request_page());
                break;
        }
    }


    private void loadFragment(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.app_frame, fragment, fragmentTag);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.app_frame);
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.iv_drawer:
                drawer.openDrawer(GravityCompat.START);
                break;

            case R.id.nav_dashboard:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Dashboard,null);
                break;

            case R.id.nav_add_leave_application:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.New_leave_application,null);
                break;

            case R.id.nav_leave_applications:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.My_leave_applications,null);
                break;

            case R.id.nav_mark_attendence:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Mark_attendance,null);
                break;

            case R.id.tv_attendance_report:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Attendance_report,null);
                break;

            case R.id.tv_request_attendance_report:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Attendance_request,null);
                break;

            case R.id.nav_changepassword:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Change_password,null);
                break;

            case R.id.nav_profile:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.User_profile,null);
                break;

            case R.id.tv_logout:
                SharedPreferenceUtils.putString(SharedPreferenceUtils.User_Creds.user_creds,"");
                startActivity(new Intent(MainActivity.this, Login.class));
                finishAffinity();
                break;

                // manager

            case R.id.nav_tv_team_leave:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Team_leave_request,null);
                break;


            case R.id.nav_gallery:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, Gallery.class));
                break;


            case R.id.nav_team_leave_report:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Team_leave_request_report,null);
                break;
        }
    }
}