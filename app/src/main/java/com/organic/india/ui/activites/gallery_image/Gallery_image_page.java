package com.organic.india.ui.activites.gallery_image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Gallery_folder_adapter;
import com.organic.india.adapter.Gallery_pic_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.pojo.dashboard.Image;
import com.organic.india.pojo.gallery_folder.Gallery_folder;
import com.organic.india.pojo.gallery_image_pojo.Data;
import com.organic.india.pojo.gallery_image_pojo.Gallery_image;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.view_photo.Viewphoto_gallery;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gallery_image_page extends AppCompatActivity {

    Gallery_pic_adapter adapter;
    List<Data> data = new ArrayList<>();

    Functions_common functions_common;

    private List<Image> images = new ArrayList<>();
    String pic_images="";

    @BindView(R.id.rcy_list)RecyclerView rcy_list;
    @BindView(R.id.tv_title)TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image_page);
        ButterKnife.bind(this);

        functions_common = new Functions_common(this);

        tv_title.setText(getIntent().getStringExtra("title"));

        get_gallery_data();


    }


    void get_gallery_data(){

        functions_common.show_loader("Getting gallery");

        adapter = new Gallery_pic_adapter(data, this, new Gallery_pic_adapter.View_pic() {
            @Override
            public void index(int pos) {
                startActivity(new Intent(Gallery_image_page.this, Viewphoto_gallery.class)
                        .putExtra("images",pic_images)
                        .putExtra("position",pos));
            }
        });
        rcy_list.setLayoutManager(new GridLayoutManager(this, 3));
        rcy_list.setAdapter(adapter);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id", Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());
        jsonObject.addProperty("folder_id",getIntent().getStringExtra("folder_id"));

        Api_instence.getRetrofitInstance().gallery_images(jsonObject).enqueue(new Callback<Gallery_image>() {
            @Override
            public void onResponse(Call<Gallery_image> call, Response<Gallery_image> response) {
                functions_common.dismiss_loader();
                data.addAll(response.body().getData()!=null?response.body().getData():data);
                adapter.notifyDataSetChanged();

                for (Data image:response.body().getData()) {
                   images.add(new Image(image.getFileName()));
                }

                pic_images = new Gson().toJson(images);
            }

            @Override
            public void onFailure(Call<Gallery_image> call, Throwable t) {
                functions_common.dismiss_loader();
            }
        });
    }

    public void back_press(View view){
        onBackPressed();
    }
}