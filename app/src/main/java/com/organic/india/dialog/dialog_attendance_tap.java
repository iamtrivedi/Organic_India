package com.organic.india.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import com.organic.india.common.Constant;
import com.organic.india.singletone.Organic_india;

public class dialog_attendance_tap {

    Context context;
    Selected_option selected_option;

    String[] items={};

    public dialog_attendance_tap(Context context, Selected_option selected_option) {
        this.context = context;
        this.selected_option = selected_option;
    }

    public void show_options(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        if (Organic_india.getInstance().getMe().getIrm().equals("manager")){

            if (Organic_india.getInstance().getMe().getOnlineAppAttendance().equalsIgnoreCase("yes")){
                items = new String[]{"Mark Attendance", "Attendance Report","Request Attendance Report"};
            }else{
                items = new String[]{"Attendance Report","Request Attendance Report"};
            }


        }else{
            if (Organic_india.getInstance().getMe().getOnlineAppAttendance().equalsIgnoreCase("YES")){
                items = new String[]{"Mark Attendance", "Attendance Report"};
            }else{
                items = new String[]{"Attendance Report"};
            }
        }

        int checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){

                        switch (items[which].toString()){

                            case "Mark Attendance":
                                selected_option.option(Constant.Route.Mark_attendance);
                                break;

                            case "Attendance Report":
                                selected_option.option(Constant.Route.Attendance_report);
                                break;

                            case "Request Attendance Report":
                                selected_option.option(Constant.Route.Attendance_request);
                                break;
                        }
                        dialog.dismiss();
                    }
                },250);
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


    public interface Selected_option{
        void option (int option);
    }
}
