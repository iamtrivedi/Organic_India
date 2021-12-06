package com.organic.india.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import com.organic.india.common.Constant;
import com.organic.india.singletone.Organic_india;

public class dialog_leave_tap {

    Context context;
    Selected_option selected_option;

    String[] items={};

    public dialog_leave_tap(Context context, Selected_option selected_option) {
        this.context = context;
        this.selected_option = selected_option;
    }

    public void show_options(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        if (Organic_india.getInstance().getMe().getIrm().equals("manager")){
            items = new String[]{"New Leave Application","Request Leave Report","Leave Application"};
        }else{
            items = new String[]{"New Leave Application","Leave Application"};
        }

        int checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){

                        switch (items[which].toString()){

                            case "New Leave Application":
                                selected_option.option(Constant.Route.New_leave_application);
                                break;

                            case "Team Leave Report":
                                selected_option.option(Constant.Route.Team_leave_request);
                                break;

                            case "Request Leave Report":
                                selected_option.option(Constant.Route.Team_leave_request_report);
                                break;

                            case "Leave Application":
                                selected_option.option(Constant.Route.My_leave_applications);
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


