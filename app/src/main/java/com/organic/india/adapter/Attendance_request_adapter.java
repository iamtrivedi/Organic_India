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
import com.organic.india.pojo.attendance_request.Request;
import java.util.List;

public class Attendance_request_adapter extends RecyclerView.Adapter<Attendance_request_adapter.ViewHolder> {

    List<Request> data;
    Context context;
    Actions actions;

    public Attendance_request_adapter(List<Request> data, Context context, Actions actions) {
        this.data = data;
        this.context = context;
        this.actions = actions;
    }

    @Override
    public Attendance_request_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attendance_request,parent,false);
      return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Attendance_request_adapter.ViewHolder holder, int position) {

        holder.tv_index.setText(""+(position+1));
        holder.tv_employeename.setText(data.get(position).getName());
        holder.tv_designation.setText(data.get(position).getDesignation());
        holder.tv_att_date.setText(data.get(position).getAttendanceDate());
        holder.tv_requesttime.setText(data.get(position).getRequestTime());

        holder.iv_pending.setOnClickListener(v->{
            actions.react_to_request(data.get(position),position);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_index,tv_employeename,tv_designation,tv_att_date,tv_requesttime;
        ImageView iv_pending;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_index=itemView.findViewById(R.id.tv_index);
            tv_employeename=itemView.findViewById(R.id.tv_employeename);
            tv_designation=itemView.findViewById(R.id.tv_designation);
            tv_att_date=itemView.findViewById(R.id.tv_att_date);
            tv_requesttime=itemView.findViewById(R.id.tv_requesttime);
            iv_pending=itemView.findViewById(R.id.iv_pending);
        }
    }

    public interface Actions{
        void react_to_request(Request request,int position);
    }
}
