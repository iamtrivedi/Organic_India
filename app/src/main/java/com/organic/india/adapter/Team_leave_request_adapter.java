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
import com.organic.india.pojo.team_leave_request.Data;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Team_leave_request_adapter extends RecyclerView.Adapter<Team_leave_request_adapter.ViewHolder> {

    List<Data> leave_request;
    Context context;
    Reaction reaction;

    public Team_leave_request_adapter(List<Data> leave_request, Context context, Reaction reaction) {
        this.leave_request = leave_request;
        this.context = context;
        this.reaction = reaction;
    }

    @Override
    public Team_leave_request_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attendance_request,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Team_leave_request_adapter.ViewHolder holder, int position) {

        holder.tv_leave_type.setText(leave_request.get(position).getLeaveCategory());
        holder.tv_designation.setText(leave_request.get(position).getDesignation());
        holder.tv_startdate_time.setText(leave_request.get(position).getStartDate());
        holder.tv_enddate_time.setText(leave_request.get(position).getEndDate());
        holder.tv_requesttime.setText(leave_request.get(position).getRequestTime());
        holder.tv_employeename.setText(leave_request.get(position).getName());
        holder.tv_index.setText(""+leave_request.get(position).getSl());

        holder.iv_pending.setOnClickListener(v->{
            reaction.action(leave_request.get(position),position);
        });
    }

    @Override
    public int getItemCount() {
        return leave_request.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_index)TextView tv_index;
        @BindView(R.id.tv_employeename)TextView tv_employeename;
        @BindView(R.id.tv_designation)TextView tv_designation;
        @BindView(R.id.tv_leave_type)TextView tv_leave_type;
        @BindView(R.id.tv_startdate_time)TextView tv_startdate_time;
        @BindView(R.id.tv_enddate_time)TextView tv_enddate_time;
        @BindView(R.id.tv_requesttime)TextView tv_requesttime;
        @BindView(R.id.iv_pending)ImageView iv_pending;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface Reaction{
        void action(Data leave,int position);
    }
}
