package com.organic.india.ui.fragments.user_profile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.organic.india.R;
import com.organic.india.adapter.Profile_detail_adapter;
import com.organic.india.common.Constant;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class User_profile extends Fragment{

    View view;
    Unbinder unbinder;
    @BindView(R.id.rcy_detail)RecyclerView rcy_detail;

    public User_profile(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.fragment_user_profile, container, false);
        unbinder = ButterKnife.bind(this,view);

        rcy_detail.setAdapter(new Profile_detail_adapter(Constant.profile_dataList(),getContext()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}