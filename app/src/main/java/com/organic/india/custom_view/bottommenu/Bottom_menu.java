package com.organic.india.custom_view.bottommenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.common.Constant;
import com.organic.india.dialog.dialog_attendance_tap;
import com.organic.india.dialog.dialog_leave_tap;
import com.organic.india.ui.activites.main.MainActivity;
import com.organic.india.ui.activity.attendance_report.Attendance_report_activity;
import com.organic.india.ui.activity.attendance_request.Attendance_request_activity;
import com.organic.india.ui.activity.mark_attendance.Mark_attendance_activity;
import com.organic.india.ui.activity.my_leave_application.My_leave_application_activity;
import com.organic.india.ui.activity.new_leave_application.New_leave_application_activity;
import com.organic.india.ui.activity.team_leave_request_report.Team_leave_request_leave_activity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Bottom_menu extends FrameLayout {

    public static final  int Home=0;
    public static final  int Leave=1;
    public static final  int Attendance=2;
    List<Bottom_step> bottom_menu = new ArrayList<>();

    @BindView(R.id.rcy_menu_list)RecyclerView rcy_menu_list;

    Bottom_step_adapter adapter;

    public Bottom_menu(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
        setupAttributes(attrs);
    }

    private void initView(){
        inflate(getContext(), R.layout.view_registration_steps, this);
        ButterKnife.bind(this);

        bottom_menu.add(new Bottom_step("Home", R.drawable.ic_home_svg,Home));
        bottom_menu.add(new Bottom_step("Leave",R.drawable.ic_work_off,Leave));
        bottom_menu.add(new Bottom_step("Attendance",R.drawable.ic_calendar_svg,Attendance));

        adapter = new Bottom_step_adapter(bottom_menu, getContext(), new Bottom_step_adapter.Tap_menu() {
            @Override
            public void menu(Bottom_step menu){
               navigate(menu);
            }
        });

        rcy_menu_list.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rcy_menu_list.setAdapter(adapter);

    }

    private void setupAttributes(AttributeSet attrs){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.bottom_menu, 0, 0);
        try {
        }finally{
            a.recycle();
        }
    }

    private void navigate(Bottom_step menu){

        Activity a = (Activity)getContext();

        switch (menu.getRoute()){

            case Home:
                a.startActivity(new Intent(a, MainActivity.class));
                a.finishAffinity();
                break;

            case Leave:

                new dialog_leave_tap(getContext(), new dialog_leave_tap.Selected_option() {
                    @Override
                    public void option(int option) {

                        switch (option){

                            case Constant.Route.New_leave_application:
                                if (!New_leave_application_activity.class.isInstance(a)){
                                    a.startActivity(new Intent(a, New_leave_application_activity.class));
                                }
                                //a.finish();
                                break;

                            case Constant.Route.Team_leave_request:
                                break;

                            case Constant.Route.Team_leave_request_report:

                                if (!Team_leave_request_leave_activity.class.isInstance(a)){
                                    a.startActivity(new Intent(a, Team_leave_request_leave_activity.class));
                                }
                                //a.finish();
                                break;

                            case Constant.Route.My_leave_applications:

                                if (!My_leave_application_activity.class.isInstance(a)){
                                    a.startActivity(new Intent(a, My_leave_application_activity.class));
                                }
                                //a.finish();
                                break;

                        }

                    }
                }).show_options();
                break;

            case Attendance:
                new dialog_attendance_tap(getContext(), new dialog_attendance_tap.Selected_option() {
                    @Override
                    public void option(int option) {
                        switch (option){

                            case Constant.Route.Mark_attendance:

                                if (!Mark_attendance_activity.class.isInstance(a)){
                                    a.startActivity(new Intent(a, Mark_attendance_activity.class));
                                }
                                //a.finish();
                                break;

                            case Constant.Route.Attendance_report:
                                if (!Attendance_report_activity.class.isInstance(a)){
                                    a.startActivity(new Intent(a, Attendance_report_activity.class));
                                }
                                break;

                            case Constant.Route.Attendance_request:

                                if (!Attendance_request_activity.class.isInstance(a)){
                                    a.startActivity(new Intent(a, Attendance_request_activity.class));
                                }
                                //a.finish();
                                break;

                        }
                    }
                }).show_options();
                break;
        }

    }
}
