package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        String date_time = data.get(position).getAttendanceDate()+"\n"+
                " Check in : "+data.get(position).getInTimeRequest()+"\n"+
                " Check out : "+data.get(position).getOutTimeRequest();


        holder.tv_index.setText(""+((position+1)));
        holder.tv_employeename.setText(data.get(position).getName());
        holder.tv_designation.setText(data.get(position).getDesignation());
        holder.tv_date_time.setText(date_time);

        holder.tv_react_to.setOnClickListener(v->{
            actions.react_to_request(data.get(position),position);
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_index,tv_employeename,tv_designation,tv_date_time,tv_react_to;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_index=itemView.findViewById(R.id.tv_index);
            tv_employeename=itemView.findViewById(R.id.tv_employeename);
            tv_designation=itemView.findViewById(R.id.tv_designation);
            tv_date_time=itemView.findViewById(R.id.tv_date_time);
            tv_react_to=itemView.findViewById(R.id.tv_react_to);
        }
    }

    public interface Actions{
        void react_to_request(Request request,int position);
    }
}