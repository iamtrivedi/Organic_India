package com.organic.india.ui.activites.view_photo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.organic.india.R;
import com.organic.india.adapter.Image_slider_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.pojo.dashboard.Image;
import com.organic.india.ui.fragments.image_slide.Image_slide_item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Viewphoto_gallery extends AppCompatActivity {

    Image_slider_adapter adapter;
    private List<Image> images = new ArrayList<>();

    @BindView(R.id.view_pager)ViewPager viewPager;

    boolean is_link=false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewphoto_gallery);
        ButterKnife.bind(this);

        adapter = new Image_slider_adapter(getSupportFragmentManager());

        is_link=getIntent().getBooleanExtra("is_link",false);

        if (is_link){
            List<Image> list = new Gson().fromJson(getIntent().getStringExtra("images"), new TypeToken<List<Image>>(){}.getType());
            images.addAll(list);
            for (Image image :images) {
                adapter.addFragment(new Image_slide_item(image.getImg(),true,0),"");
            }
        }else{
            for (int resource  : Functions_common.slides()){
                adapter.addFragment(new Image_slide_item("",false,resource),"");
            }
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("position",0));
    }
}