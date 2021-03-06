package com.organic.india.ui.fragments.dashboard;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organic.india.R;
import com.organic.india.adapter.Image_slider_adapter;
import com.organic.india.adapter.Organization_chart_adapter;
import com.organic.india.adapter.Photo_gallery_adapter;
import com.organic.india.adapter.Press_coverage_adapter;
import com.organic.india.adapter.Slider_adapter;
import com.organic.india.common.Functions_common;
import com.organic.india.data.Api_instence;
import com.organic.india.dialog.Gallery_dialog;
import com.organic.india.dialog.Gallery_dialog_local_image;
import com.organic.india.dialog.Update_app;
import com.organic.india.pojo.dashboard.AppVersion;
import com.organic.india.pojo.dashboard.Image;
import com.organic.india.pojo.dashboard.OrganizationChart;
import com.organic.india.pojo.dashboard.Pdf;
import com.organic.india.pojo.dashboard.PendingRequest;
import com.organic.india.singletone.Organic_india;
import com.organic.india.ui.activites.view_photo.Viewphoto_gallery;
import com.organic.india.utils.helper.CenterZoomLayoutManager;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends Fragment implements View.OnClickListener {

    View view;
    Unbinder unbinder;

    private List<Pdf> pdfs = new ArrayList<>();
    Press_coverage_adapter press_coverage_adapter;

    private List<Image> images = new ArrayList<>();
    Photo_gallery_adapter photo_gallery_adapter;

    private List<OrganizationChart> chartArrayList = new ArrayList<>();
    Organization_chart_adapter organization_chart_adapter;

    @BindView(R.id.main_scroll)NestedScrollView main_scroll;
    @BindView(R.id.rcy_press_coverage)RecyclerView rcy_press_coverage;
    @BindView(R.id.rcy_photo_gallery)RecyclerView rcy_photo_gallery;
    @BindView(R.id.rcy_orgnization)RecyclerView rcy_orgnization;

    @BindView(R.id.ll_show_press_coverage)RelativeLayout ll_show_press_coverage;
    @BindView(R.id.iv_show_press_coverage)ImageView iv_show_press_coverage;
    @BindView(R.id.ll_show_photo_gallery)RelativeLayout ll_show_photo_gallery;
    @BindView(R.id.iv_show_photo_gallery)ImageView iv_show_photo_gallery;

    @BindView(R.id.ll_press_coverage)RelativeLayout ll_press_coverage_list;
    @BindView(R.id.ll_gallery_container)RelativeLayout ll_gallery_container;

    @BindView(R.id.iv_trust_img)ImageView iv_trust_img;
    @BindView(R.id.position)TextView tv_position;
    @BindView(R.id.tv_name)TextView tv_name;
    @BindView(R.id.ll_row_chart)RelativeLayout ll_row_chart;
    @BindView(R.id.imageslide)RecyclerView imageslide;
    @BindView(R.id.ll_oi_journey)LinearLayout ll_oi_journey;

    String pic_images = "";

    Dashboard_data dashboard_data;
    public Dashboard(Dashboard_data dashboard_data) {
        this.dashboard_data = dashboard_data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder= ButterKnife.bind(this,view);

        press_coverage_adapter = new Press_coverage_adapter(getContext(),pdfs);

        photo_gallery_adapter = new Photo_gallery_adapter(images, getContext(), new Photo_gallery_adapter.Pic_view() {
            @Override
            public void pic(int pos,Image image){

                new Gallery_dialog(getContext(), image, new Gallery_dialog.React() {
                    @Override
                    public void full_image() {

                        startActivity(new Intent(getContext(), Viewphoto_gallery.class)
                                .putExtra("images",pic_images)
                                .putExtra("position",pos)
                                .putExtra("is_link",true));
                    }
                }).show();
            }
        });

        organization_chart_adapter=new Organization_chart_adapter(chartArrayList,getContext());

        rcy_press_coverage.setAdapter(press_coverage_adapter);

        rcy_photo_gallery.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rcy_photo_gallery.setAdapter(photo_gallery_adapter);


        rcy_orgnization.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rcy_orgnization.setAdapter(organization_chart_adapter);

        create_dashboard();

        ll_show_press_coverage.setOnClickListener(this::onClick);
        ll_show_photo_gallery.setOnClickListener(this::onClick);

        return view;


    }


    void create_dashboard(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employee_id",Organic_india.getInstance().getMe().getEmployeeId());
        jsonObject.addProperty("employee_code", Organic_india.getInstance().getMe().getEmployeeCode());

        Log.e("params_send",""+jsonObject.toString());

        Api_instence.getRetrofitInstance().employee_dashboard(jsonObject)
                .enqueue(new Callback<com.organic.india.pojo.dashboard.Dashboard>() {
                    @Override
                    public void onResponse(Call<com.organic.india.pojo.dashboard.Dashboard> call, Response<com.organic.india.pojo.dashboard.Dashboard> response) {
                     if (response.isSuccessful()){
                       pdfs.removeAll(pdfs);
                       images.removeAll(images);
                       chartArrayList.removeAll(chartArrayList);
                       press_coverage_adapter.notifyDataSetChanged();
                       organization_chart_adapter.notifyDataSetChanged();
                       photo_gallery_adapter.notifyDataSetChanged();

                       pdfs.addAll(response.body().getPdf()!=null?response.body().getPdf():pdfs);
                       press_coverage_adapter.notifyDataSetChanged();

                       images.addAll(response.body().getImages()!=null?response.body().getImages():images);
                       photo_gallery_adapter.notifyDataSetChanged();

                       pic_images = new Gson().toJson(images);

                       Log.e("pic_images",""+pic_images);

                       for (int i=0;i<response.body().getOrganizationChart().size();i++){
                           if (i==0){

                           //    Picasso.get().load(response.body().getOrganizationChart().get(0).getImg()).into(iv_trust_img);

                               Glide.with(getActivity()).load(response.body().getOrganizationChart().get(0).getImg())
                                       .placeholder(R.drawable.image_placeholder).apply(new RequestOptions()).into(iv_trust_img);

                               tv_name.setText(response.body().getOrganizationChart().get(0).getName());
                               tv_position.setText(response.body().getOrganizationChart().get(0).getDesignation());
                               ll_row_chart.setVisibility(View.VISIBLE);
                               continue;
                           }
                           chartArrayList.add(response.body().getOrganizationChart().get(i));
                       }
                         organization_chart_adapter.notifyDataSetChanged();

                         try {
                             PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                             String version = pInfo.versionName;

                             Log.e("app_version",""+version);

                             for (AppVersion appVersion:response.body().getAppVersion()){
                                 if (!appVersion.getVersionCode().equalsIgnoreCase(version)){
                                     new Update_app(getContext()).show();
                                     break;
                                 }else{
                                     Toast.makeText(getContext(), "Welcome to Organic India", Toast.LENGTH_SHORT).show();
                                     break;
                                 }
                             }

                         } catch (PackageManager.NameNotFoundException e) {
                             e.printStackTrace();
                         }
                         ll_oi_journey.setVisibility(View.VISIBLE);

                         dashboard_data.manager_roles_notifications(response.body().getPendingRequest());

                     }
                    }
                    @Override
                    public void onFailure(Call<com.organic.india.pojo.dashboard.Dashboard> call, Throwable t) {

                    }
                });

        imageslide.setLayoutManager(new CenterZoomLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        imageslide.setAdapter(new Slider_adapter(Functions_common.slides(), getContext(), new Slider_adapter.Selected_image() {
            @Override
            public void image(int pos) {
                new Gallery_dialog_local_image(getContext(), Functions_common.slides().get(pos), new Gallery_dialog_local_image.React() {
                    @Override
                    public void full_image() {

                        startActivity(new Intent(getContext(), Viewphoto_gallery.class)
                                .putExtra("images",pic_images)
                                .putExtra("position",pos)
                                .putExtra("is_link",false));
                    }
                }).show();
            }
        }));
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){

          case R.id.ll_show_press_coverage:
              ll_press_coverage_list.setVisibility(ll_press_coverage_list.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
              iv_show_press_coverage.animate().rotation(ll_press_coverage_list.getVisibility()==View.VISIBLE?180:0).start();
              main_scroll.post(new Runnable() {
                  @Override
                  public void run() {
                      main_scroll.fullScroll(View.FOCUS_DOWN);
                  }
              });
              break;

          case R.id.ll_show_photo_gallery:
              ll_gallery_container.setVisibility(ll_gallery_container.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
              iv_show_photo_gallery.animate().rotation(ll_gallery_container.getVisibility()==View.VISIBLE?180:0).start();
              main_scroll.post(new Runnable() {
                  @Override
                  public void run() {
                      main_scroll.fullScroll(View.FOCUS_DOWN);
                  }
              });
              break;
      }
    }

    public interface Dashboard_data{
        void manager_roles_notifications(PendingRequest pendingRequest);
    }
}