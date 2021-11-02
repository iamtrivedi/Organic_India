package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.pojo.leave_applications.Application;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class My_leave_list_adapter extends RecyclerView.Adapter<My_leave_list_adapter.ViewHolder> {

    Context context;
    List<Application> data;

    public My_leave_list_adapter(Context context, List<Application> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public My_leave_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_leave,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_leave_list_adapter.ViewHolder holder, int position) {

        holder.tv_reason.setText(data.get(position).getReason());
        holder.tv_start_date.setText(data.get(position).getStartDate());
        holder.tv_end_date.setText(data.get(position).getEndDate());
        holder.tv_leave_days.setText(""+data.get(position).getLeaveDays());
        holder.tv_leave_cat.setText(data.get(position).getLeaveCategory());
        holder.tv_created_at.setText(data.get(position).getCreatedAt());


        switch (data.get(position).getStatus()){

         case "Pending":
             holder.iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.pending_icon));
             break;

         case"Accepted":
             holder.iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.accepted_btn));
             break;

         case "Not Accepted":
             holder.iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.rejected_btn));
             break;

     }
  }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_status)ImageView iv_status;
        @BindView(R.id.tv_reason)TextView tv_reason;
        @BindView(R.id.tv_start_date)TextView tv_start_date;
        @BindView(R.id.tv_end_date)TextView tv_end_date;
        @BindView(R.id.tv_leave_days)TextView tv_leave_days;
        @BindView(R.id.tv_leave_cat)TextView tv_leave_cat;
        @BindView(R.id.tv_created_at)TextView tv_created_at;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
