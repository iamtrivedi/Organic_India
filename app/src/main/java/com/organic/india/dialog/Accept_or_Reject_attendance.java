package com.organic.india.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.organic.india.R;
import com.organic.india.pojo.attendance_request.Request;

public class Accept_or_Reject_attendance extends Dialog {

    Request request;
    React react;

    TextView tv_date_time,tv_designation,tv_reason,tv_employeename,tv_reject,tv_accept;


    public Accept_or_Reject_attendance(@NonNull Context context, Request request, React react) {
        super(context);
        this.request = request;
        this.react = react;

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setTitle(null);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_react_on_attendance, null);
        setContentView(view);

        tv_date_time=view.findViewById(R.id.tv_date_time);
        tv_designation=view.findViewById(R.id.tv_designation);
        tv_employeename=view.findViewById(R.id.tv_employeename);
        tv_reason=view.findViewById(R.id.tv_reason);
        tv_reject=view.findViewById(R.id.tv_reject);
        tv_accept=view.findViewById(R.id.tv_accept);


        String date_time = request.getAttendanceDate()+"\n"+
                " Check in : "+request.getInTimeRequest()+"\n"+
                " Check out : "+request.getOutTimeRequest();

        tv_employeename.setText(request.getName());
        tv_designation.setText(request.getDesignation());
        tv_date_time.setText(date_time);

        tv_reason.setText(request.getReasonName());


        tv_accept.setOnClickListener(v->{
            react.reaction(1);
            this.dismiss();
        });

        tv_reject.setOnClickListener(v->{
            react.reaction(2);
            this.dismiss();
        });
    }


    public interface React{
        void reaction(int value);
    }
}
