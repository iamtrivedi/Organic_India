package com.organic.india.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.organic.india.R;
import com.organic.india.common.Constant;
import com.organic.india.pojo.pending_leave.Data;
import java.util.List;
import java.util.Random;

public class Leave_chart_adapter extends RecyclerView.Adapter<Leave_chart_adapter.ViewHolder> {

    List<Data> chart_data;
    Context context;

    public Leave_chart_adapter(List<Data> chart_data, Context context) {
        this.chart_data = chart_data;
        this.context = context;
    }

    @Override
    public Leave_chart_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leave_chart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Leave_chart_adapter.ViewHolder holder, int position) {

      Random rn = new Random();
      holder.chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor(Constant.random_color(rn.nextInt(9)))));
      holder.chip.setText(""+chart_data.get(position).getLeaveCategory()+":"+chart_data.get(position).getPendingLeave());
    }

    @Override
    public int getItemCount() {
        return chart_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chip=itemView.findViewById(R.id.chip);
        }
    }
}
