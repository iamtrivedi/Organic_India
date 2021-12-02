package com.organic.india.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.pojo.attendance_log.Log;
import com.organic.india.ui.activites.attendance_location.Attendance_location;
import java.util.ArrayList;
import java.util.List;

public class Atteandance_log_adapter extends RecyclerView.Adapter<Atteandance_log_adapter.ViewHolder> {

    private List<Log> logs= new ArrayList<>();
    Context context;

    public Atteandance_log_adapter(List<Log> logs, Context context) {
        this.logs = logs;
        this.context = context;
    }

    @Override
    public Atteandance_log_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attendance_log,parent,false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Atteandance_log_adapter.ViewHolder holder, int position) {

        holder.tv_in_log_type.setText(logs.get(position).getAttendanceType());
        holder.tv_out_log_type.setText(logs.get(position).getAttendanceType());


        holder.tv_in_time.setText(logs.get(position).getInTime()!=null?logs.get(position).getInTime():"-");
        holder.tv_outtime.setText(logs.get(position).getOutTime()!=null?logs.get(position).getOutTime():"-");

        holder.tv_in_ip.setText("-"+logs.get(position).getInTimeIp()!=null?logs.get(position).getInTimeIp():"-");
        holder.tv_out_ip.setText("-"+logs.get(position).getOutTimeIp()!=null?logs.get(position).getOutTimeIp():"-");

        holder.ll_in_time.setVisibility(logs.get(position).getInTime()!=null?View.VISIBLE:View.GONE);
        holder.ll_out_time.setVisibility(logs.get(position).getOutTime()!=null?View.VISIBLE:View.GONE);
        holder.ll_no_log_time.setVisibility(logs.get(position).getInTime()==null && logs.get(position).getOutTime()==null?View.VISIBLE:View.GONE);

        switch (logs.get(position).getAttendanceType()){


            case "Web":
                //in time
                holder.tv_view_in_location.setVisibility(View.GONE);
                holder.ll_in_time_ip.setVisibility(View.VISIBLE);

                //out time
                holder.tv_view_out_location.setVisibility(View.GONE);
                holder.ll_out_time_ip.setVisibility(View.VISIBLE);
                break;

            case "Biometric":
                //in time
                holder.ll_in_time_ip.setVisibility(View.GONE);
                holder.tv_view_in_location.setVisibility(View.GONE);

                //out time
                holder.ll_out_time_ip.setVisibility(View.GONE);
                holder.tv_view_out_location.setVisibility(View.GONE);
                break;

            case "App":
                //in time
                holder.tv_view_in_location.setVisibility(View.VISIBLE);
                holder.ll_in_time_ip.setVisibility(View.GONE);

                //out time
                holder.tv_view_out_location.setVisibility(View.VISIBLE);
                holder.ll_out_time_ip.setVisibility(View.GONE);
                break;

        }

        holder.tv_view_in_location.setOnClickListener(v->{

            try{
                double lat_value= logs.get(position).getInTimeLat()!=null?Double.parseDouble(logs.get(position).getInTimeLat()):0.0;
                double lng_value= logs.get(position).getInTimeLong()!=null?Double.parseDouble(logs.get(position).getInTimeLong()):0.0;

                if (lat_value>0 && lng_value>0){
                    context.startActivity(new Intent(context,Attendance_location.class)
                            .putExtra("LAT",lat_value)
                            .putExtra("LNG",lng_value));
                }else{
                    Toast.makeText(context, "No In-Time Address Found", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                Toast.makeText(context, "No In-Time Address Found", Toast.LENGTH_SHORT).show();
            }

        });

        holder.tv_view_out_location.setOnClickListener(v->{



            try{
                double lat_value= logs.get(position).getOutTimeLat()!=null?Double.parseDouble(logs.get(position).getOutTimeLat()):0.0;
                double lng_value= logs.get(position).getOutTimeLong()!=null?Double.parseDouble(logs.get(position).getOutTimeLong()):0.0;

                android.util.Log.e("laata",""+lat_value+" "+lng_value);

                if (lat_value>0 && lng_value>0){
                    context.startActivity(new Intent(context,Attendance_location.class)
                            .putExtra("LAT",lat_value)
                            .putExtra("LNG",lng_value));
                }else{
                    Toast.makeText(context, "No Out-Time Address Found", Toast.LENGTH_SHORT).show();
                }


            }catch (Exception e){
                Toast.makeText(context, "No Out-Time Address Found", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout ll_no_log_time;
        TextView tv_view_out_location,tv_view_in_location,tv_in_time,tv_outtime,tv_in_ip,tv_out_ip,tv_out_log_type,tv_in_log_type;
        LinearLayout ll_in_time_ip,ll_out_time_ip;
        LinearLayout ll_out_time,ll_in_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_in_time=itemView.findViewById(R.id.ll_in_time);
            ll_out_time=itemView.findViewById(R.id.ll_out_time);
            tv_view_in_location=itemView.findViewById(R.id.tv_view_in_location);
            tv_view_out_location=itemView.findViewById(R.id.tv_view_out_location);
            tv_in_time=itemView.findViewById(R.id.tv_in_time);
            tv_outtime=itemView.findViewById(R.id.tv_outtime);
            ll_no_log_time=itemView.findViewById(R.id.ll_no_log_time);
            tv_in_ip=itemView.findViewById(R.id.tv_in_ip);
            tv_out_ip=itemView.findViewById(R.id.tv_out_ip);
            ll_in_time_ip=itemView.findViewById(R.id.ll_in_time_ip);
            ll_out_time_ip=itemView.findViewById(R.id.ll_out_time_ip);
            tv_in_log_type=itemView.findViewById(R.id.tv_in_log_type);
            tv_out_log_type=itemView.findViewById(R.id.tv_out_log_type);
        }
    }
}
