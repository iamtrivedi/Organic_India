package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.organic.india.R;
import com.organic.india.pojo.User_profile_data;
import java.util.List;

public class Profile_detail_adapter extends RecyclerView.Adapter<Profile_detail_adapter.ViewHolder> {

    List<User_profile_data> profile_dataList;
    Context context;

    public Profile_detail_adapter(List<User_profile_data> profile_dataList, Context context) {
        this.profile_dataList = profile_dataList;
        this.context = context;
    }

    @Override
    public Profile_detail_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_profile_detail_card,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Profile_detail_adapter.ViewHolder holder, int position) {

        holder.tv_title.setText(profile_dataList.get(position).getTitle());
        holder.ll_card.setBackgroundColor(position%2==0?context.getResources().getColor(R.color.white):context.getResources().getColor(R.color.lighest_grey));

        if (profile_dataList.get(position).isIs_pic()){

            holder.iv_pic.setVisibility(View.VISIBLE);
            holder.tv_info.setVisibility(View.GONE);
            Glide.with(context).load(profile_dataList.get(position).getInfo())
                    .placeholder(R.drawable.image_placeholder).apply(new RequestOptions()).into(holder.iv_pic);
        }else{

            holder.iv_pic.setVisibility(View.GONE);
            holder.tv_info.setVisibility(View.VISIBLE);
            holder.tv_info.setText(profile_dataList.get(position).getInfo());
        }
    }

    @Override
    public int getItemCount() {
        return profile_dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title,tv_info;
        LinearLayout ll_card;
        ImageView iv_pic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_info=itemView.findViewById(R.id.tv_info);
            ll_card=itemView.findViewById(R.id.ll_card);
            iv_pic=itemView.findViewById(R.id.iv_pic);
        }
    }
}
