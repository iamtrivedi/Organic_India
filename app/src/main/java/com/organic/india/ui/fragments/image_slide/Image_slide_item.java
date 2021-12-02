package com.organic.india.ui.fragments.image_slide;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.organic.india.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Image_slide_item extends Fragment {

    @BindView(R.id.iv_image)ImageView iv_image;
    @BindView(R.id.progressBar)ProgressBar progressBar;

    String image_link="";
    View view;
    boolean is_link=true;
    int resorces=0;

    public Image_slide_item(String image_link, boolean is_link, int resorces) {
        this.image_link = image_link;
        this.is_link = is_link;
        this.resorces = resorces;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_image_slide_item, container, false);
        ButterKnife.bind(this,view);

        if(is_link){
            Glide.with(this)
                    .asBitmap()
                    .load(image_link)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            iv_image.setImageBitmap(resource);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }else{
            progressBar.setVisibility(View.GONE);
            iv_image.setImageResource(resorces);
        }
        return view;
    }
}