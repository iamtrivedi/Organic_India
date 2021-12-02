package com.organic.india.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.organic.india.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Gallery_dialog_local_image  extends Dialog {

    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_zoom)ImageView iv_zoom;
    @BindView(R.id.iv_close)ImageView iv_close;

    int image;
    React react;

    public Gallery_dialog_local_image(@NonNull Context context, int image, React react) {
        super(context);
        this.image = image;
        this.react = react;

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setTitle(null);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_gallery_view, null);
        setContentView(view);
        ButterKnife.bind(this,view);

        iv_image.setImageResource(image);
        progressBar.setVisibility(View.GONE);

        iv_zoom.setOnClickListener(v->{
            react.full_image();
        });

        iv_close.setOnClickListener(v->{
            cancel();
        });
    }

    public interface React{
        void full_image();
    }
}
