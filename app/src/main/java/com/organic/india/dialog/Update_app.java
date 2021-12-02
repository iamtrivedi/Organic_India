package com.organic.india.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.organic.india.R;

public class Update_app extends Dialog {

    public Update_app(@NonNull Context context) {
        super(context);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setTitle(null);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_app, null);
        setContentView(view);

        TextView tv_upgrade=view.findViewById(R.id.tv_upgrade);

        tv_upgrade.setOnClickListener(v->{
            Toast.makeText(context, "App will be update in production mode", Toast.LENGTH_SHORT).show();
        });

    }
}
