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
import com.organic.india.dialog.dialog_attendance_tap;
import com.organic.india.dialog.dialog_leave_tap;
import com.organic.india.pojo.attendance.Attendance;
import com.organic.india.pojo.dashboard.PendingRequest;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.gallery.Gallery;
import com.organic.india.ui.activites.login.Login;
import com.organic.india.ui.activites.policies.Policies_page;
import com.organic.india.ui.activites.whoswho.Whosewho_page;
import com.organic.india.ui.activity.attendance_added.Attendance_added_activity;
import com.organic.india.ui.activity.attendance_report.Attendance_report_activity;
import com.organic.india.ui.activity.attendance_request.Attendance_request_activity;
import com.organic.india.ui.activity.change_password.Change_password_activity;
import com.organic.india.ui.activity.mark_attendance.Mark_attendance_activity;
import com.organic.india.ui.activity.my_leave_application.My_leave_application_activity;
import com.organic.india.ui.activity.new_leave_application.New_leave_application_activity;
import com.organic.india.ui.activity.team_leave_request_report.Team_leave_request_leave_activity;
import com.organic.india.ui.activity.user_profile.User_profile_activity;
import com.organic.india.ui.fragments.dashboard.Dashboard;
import java.io.Serializable;
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
    @BindView(R.id.nav_whowho)TextView nav_whowho;
    @BindView(R.id.nav_policy)TextView nav_policy;
    @BindView(R.id.tv_leave_count)TextView tv_leave_count;
    @BindView(R.id.tv_attendance_count)TextView tv_attendance_count;

    @BindView(R.id.nav_tv_team_leave)TextView nav_tv_team_leave;
    @BindView(R.id.nav_team_leave_report)TextView nav_team_leave_report;

    @BindView(R.id.ll_home)LinearLayout ll_home;
    @BindView(R.id.ll_leave)LinearLayout ll_leave;
    @BindView(R.id.ll_attendance)LinearLayout ll_attendance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_drawer);
        ButterKnife.bind(this);

        load_frame(Constant.Route.Dashboard,null);

        ll_manager_leave_option.setVisibility(Organic_india.getInstance().getMe().getIrm().equals("manager")?View.VISIBLE:View.GONE);
        ll_manager_attendance_option.setVisibility(Organic_india.getInstance().getMe().getIrm().equals("manager")?View.VISIBLE:View.GONE);
        nav_mark_attendence.setVisibility(Organic_india.getInstance().getMe().getOnlineAppAttendance().equalsIgnoreCase("yes")?View.VISIBLE:View.GONE);


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
        nav_whowho.setOnClickListener(this);
        nav_policy.setOnClickListener(this);

        ll_home.setOnClickListener(this);
        ll_leave.setOnClickListener(this);
        ll_attendance.setOnClickListener(this);

    }

    private void load_frame(int route,Object object){
        switch (route) {

            case Constant.Route.Dashboard:
                loadFragment(new Dashboard(new Dashboard.Dashboard_data() {
                    @Override
                    public void manager_roles_notifications(PendingRequest pendingRequest) {
                        try {

                            tv_attendance_count.setText(""+pendingRequest.getAttendanceRequest());
                            tv_leave_count.setText(""+pendingRequest.getLeaveRequest());

                            tv_leave_count.setVisibility(pendingRequest.getLeaveRequest()>0?View.VISIBLE:View.GONE);
                            tv_attendance_count.setVisibility(pendingRequest.getAttendanceRequest()>0?View.VISIBLE:View.GONE);

                        }catch (Exception e){
                            tv_leave_count.setVisibility(View.GONE);
                            tv_attendance_count.setVisibility(View.GONE);
                        }
                    }
                }));
                break;

            case Constant.Route.New_leave_application:

                startActivity(new Intent(MainActivity.this, New_leave_application_activity.class));

                break;

             case Constant.Route.My_leave_applications:

                 startActivity(new Intent(MainActivity.this, My_leave_application_activity.class));


                break;

             case Constant.Route.Mark_attendance:

                 startActivity(new Intent(MainActivity.this, Mark_attendance_activity.class));

                 break;


            case Constant.Route.Attendance_report:

                startActivity(new Intent(MainActivity.this, Attendance_report_activity.class));

                break;



            case Constant.Route.Change_password:

                startActivity(new Intent(MainActivity.this, Change_password_activity.class));

                break;

            case Constant.Route.Attendance_added:

                startActivity(new Intent(MainActivity.this, Attendance_added_activity.class)
                .putExtra("Attendance_added",(Serializable)(Attendance) object));

                break;

            case Constant.Route.User_profile:

                startActivity(new Intent(MainActivity.this, User_profile_activity.class));

                break;

            case Constant.Route.Attendance_request:

                startActivity(new Intent(MainActivity.this, Attendance_request_activity.class));
                break;

            case Constant.Route.Team_leave_request:

                Toast.makeText(MainActivity.this, "coming soon", Toast.LENGTH_SHORT).show();


                break;


            case Constant.Route.Team_leave_request_report:

                startActivity(new Intent(MainActivity.this, Team_leave_request_leave_activity.class));

                break;



            case Constant.Route.BM_home:
                 loadFragment(new Dashboard(new Dashboard.Dashboard_data() {
                     @Override
                     public void manager_roles_notifications(PendingRequest pendingRequest) {
                         try {

                             tv_attendance_count.setText(""+pendingRequest.getAttendanceRequest());
                             tv_leave_count.setText(""+pendingRequest.getLeaveRequest());

                             tv_leave_count.setVisibility(pendingRequest.getLeaveRequest()>0?View.VISIBLE:View.GONE);
                             tv_attendance_count.setVisibility(pendingRequest.getAttendanceRequest()>0?View.VISIBLE:View.GONE);

                         }catch (Exception e){
                             tv_leave_count.setVisibility(View.GONE);
                             tv_attendance_count.setVisibility(View.GONE);
                         }
                     }
                 }));
                break;



            case Constant.Route.BM_leave:
                new dialog_leave_tap(this, new dialog_leave_tap.Selected_option() {
                    @Override
                    public void option(int option) {
                        load_frame(option,null);
                    }
                }).show_options();
               break;



            case Constant.Route.BM_attendace:

                new dialog_attendance_tap(this, new dialog_attendance_tap.Selected_option() {
                    @Override
                    public void option(int option) {
                        load_frame(option,null);
                    }
                }).show_options();

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

            case R.id.nav_whowho:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, Whosewho_page.class));
                break;

            case R.id.nav_policy:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, Policies_page.class));
                break;

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
              //  load_frame(Constant.Route.Team_leave_request,null);
                break;


            case R.id.nav_gallery:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, Gallery.class));
                break;


            case R.id.nav_team_leave_report:
                drawer.closeDrawer(GravityCompat.START);
                load_frame(Constant.Route.Team_leave_request_report,null);
                break;


            case R.id.ll_home:
                load_frame(Constant.Route.BM_home,null);
                break;


            case R.id.ll_leave:
                load_frame(Constant.Route.BM_leave,null);
                break;


            case R.id.ll_attendance:
                load_frame(Constant.Route.BM_attendace,null);
                break;
        }
    }
}