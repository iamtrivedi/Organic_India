package com.organic.india.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.organic.india.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Toolbar_view extends FrameLayout {

    @BindView(R.id.tv_title)TextView tv_title;
    @BindView(R.id.iv_back)ImageView iv_back;

    public Toolbar_view(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
        setupAttributes(attrs);
    }

    private void initView(){
        inflate(getContext(), R.layout.tool_layout, this);
        ButterKnife.bind(this);

        iv_back.setOnClickListener(v->{
            Activity a = (Activity)getContext();
            a.onBackPressed();
        });
    }


    public void settitle(String title){
        tv_title.setText(title);
    }


    private void setupAttributes(AttributeSet attrs){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.Simpletoolbar, 0, 0);
        try {
            tv_title.setText(a.getString(R.styleable.Simpletoolbar_title));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();

    }
}
