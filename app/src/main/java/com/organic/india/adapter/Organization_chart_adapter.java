package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.organic.india.R;
import com.organic.india.pojo.dashboard.OrganizationChart;
import java.util.List;

public class Organization_chart_adapter extends RecyclerView.Adapter<Organization_chart_adapter.ViewHolder> {

    private List<OrganizationChart> data;
    Context context;

    public Organization_chart_adapter(List<OrganizationChart> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Organization_chart_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chart_card,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Organization_chart_adapter.ViewHolder holder, int position) {

        holder.ll_row_chart.setVisibility(View.VISIBLE);

        Glide.with(context).load(data.get(position).getImg())
                .placeholder(R.drawable.image_placeholder).apply(new RequestOptions()).into(holder.iv_trust_img);

        holder.tv_name.setText(data.get(position).getName().length()>14?data.get(position).getName():data.get(position).getName()+"\n");
        holder.tv_position.setText(data.get(position).getDesignation());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout ll_row_chart;
        ImageView iv_trust_img;
        TextView tv_position,tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_trust_img=itemView.findViewById(R.id.iv_trust_img);
            tv_position=itemView.findViewById(R.id.position);
            tv_name=itemView.findViewById(R.id.tv_name);
            ll_row_chart=itemView.findViewById(R.id.ll_row_chart);
        }
    }
}
