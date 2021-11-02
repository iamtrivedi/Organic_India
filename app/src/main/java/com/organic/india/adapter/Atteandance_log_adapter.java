package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.pojo.attendance_log.Log;
import com.organic.india.singletone.Organic_india;
import com.organic.india.utils.helper.Helpers;
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
        holder.tv_index.setText(""+((position+1)));

        holder.tv_date.setText(logs.get(position).getAttendanceDate());
        holder.tv_in_time.setText(logs.get(position).getInTime()!=null?logs.get(position).getInTime():"-");
        holder.tv_outtime.setText(logs.get(position).getOutTime()!=null?logs.get(position).getOutTime():"-");
        holder.tv_in_time_ip.setText(logs.get(position).getInTimeIp()!=null?logs.get(position).getInTimeIp():"-");
        holder.tv_out_time_ip.setText(logs.get(position).getOutTimeIp()!=null?logs.get(position).getOutTimeIp():"-");


        holder.tv_view_location.setVisibility(logs.get(position).getInTimeLat()==null || logs.get(position).getInTimeLong()==null?View.GONE:View.VISIBLE);
        holder.tv_view_out_location.setVisibility(logs.get(position).getOutTimeLat()==null || logs.get(position).getOutTimeLog()==null?View.GONE:View.VISIBLE);

        holder.tv_view_location.setOnClickListener(v->{

            try{
                Helpers.open_google_map(Double.parseDouble(logs.get(position).getInTimeLat()),Double.parseDouble(logs.get(position).getInTimeLong()),
                        Organic_india.getInstance().getMe().getName()+"'s In time location",context);
            }catch (NullPointerException e){
                Toast.makeText(context, "No In-Time Address Found", Toast.LENGTH_SHORT).show();
            }


//           double latitute = Double.parseDouble(logs.get(position).getInTimeLat()!=null?logs.get(position).getInTimeLat():"0.0");
//           double longititute = Double.parseDouble(logs.get(position).getInTimeLong()!=null?logs.get(position).getInTimeLong():"0.0");

//           if (latitute!=0.0 && longititute!=0.0){
//               Helpers.open_google_map(latitute,longititute,
//                       Organic_india.getInstance().getMe().getName()+"'s In time location",context);
//           }else{
//               Toast.makeText(context, "No In-Time Address Found", Toast.LENGTH_SHORT).show();
//           }

        });


        holder.tv_view_out_location.setOnClickListener(v->{

            try{
                Helpers.open_google_map(Double.parseDouble(logs.get(position).getOutTimeLat()),Double.parseDouble(logs.get(position).getOutTimeLog()),
                        Organic_india.getInstance().getMe().getName()+"'s In time location",context);
            }catch (NullPointerException e){
                Toast.makeText(context, "No Out-Time Address Found", Toast.LENGTH_SHORT).show();
            }



//            if (logs.get(position).getOutTimeLat()!=null && logs.get(position).getOutTime()!=null){
//
//            }else{
//                Toast.makeText(context, "No Out-Time Address Found", Toast.LENGTH_SHORT).show();
//            }


//            double latitute = Double.parseDouble(logs.get(position).getOutTimeLat()!=null?logs.get(position).getOutTimeLat():"0.0");
//            double longititute = Double.parseDouble(logs.get(position).getOutTimeLog()!=null?logs.get(position).getOutTimeLog():"0.0");
//
//            if (latitute!=0.0 && longititute!=0.0){
//
//                Helpers.open_google_map(latitute,longititute,
//                        Organic_india.getInstance().getMe().getName()+"'s Out time location",context);
//
////                context.startActivity(new Intent(context, Attendance_location.class)
////                        .putExtra("LAT_LNG","LAT_LNG")
////                        .putExtra("LAT",latitute)
////                        .putExtra("LNG",longititute));
//            }else{
//                Toast.makeText(context, "No Out-Time Address Found", Toast.LENGTH_SHORT).show();
//            }

        });
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_out_time_ip,tv_in_time_ip,tv_outtime,tv_in_time,tv_date,tv_index,tv_view_location,tv_view_out_location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_index=itemView.findViewById(R.id.tv_index);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_in_time=itemView.findViewById(R.id.tv_in_time);
            tv_outtime=itemView.findViewById(R.id.tv_outtime);
            tv_in_time_ip=itemView.findViewById(R.id.tv_in_time_ip);
            tv_out_time_ip=itemView.findViewById(R.id.tv_out_time_ip);
            tv_view_location=itemView.findViewById(R.id.tv_view_location);
            tv_view_out_location=itemView.findViewById(R.id.tv_view_out_location);
        }
    }
}
