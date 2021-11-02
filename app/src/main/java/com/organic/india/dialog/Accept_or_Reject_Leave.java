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
import com.organic.india.pojo.team_leave_request.Data;

public class Accept_or_Reject_Leave extends Dialog {

    Data leave;
    React react;

    TextView tv_title,tv_leave_category,tv_startdate,tv_end_date,tv_reject,tv_accept;


    public Accept_or_Reject_Leave(@NonNull Context context, Data request, React react) {
        super(context);
        this.leave = request;
        this.react = react;

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setTitle(null);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_react_on_leave, null);
        setContentView(view);

        tv_title=view.findViewById(R.id.tv_title);
        tv_leave_category=view.findViewById(R.id.tv_leave_category);
        tv_startdate=view.findViewById(R.id.tv_startdate);
        tv_end_date=view.findViewById(R.id.tv_end_date);
        tv_accept=view.findViewById(R.id.tv_accept);
        tv_reject=view.findViewById(R.id.tv_reject);


        tv_leave_category.setText(leave.getLeaveCategory());
        tv_startdate.setText(leave.getStartDate());
        tv_end_date.setText(leave.getEndDate());

        tv_title.setText(leave.getLeaveCategory()+" from "+leave.getName());


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
