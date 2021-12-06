package com.organic.india.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.common.Functions_common;
import com.organic.india.pojo.team_listing.Employee;

import java.util.List;

public class Team_list_adapter extends RecyclerView.Adapter<Team_list_adapter.ViewHolder> {

    List<Employee> employees;
    Team_player team_player;
    Context context;

    public Team_list_adapter(List<Employee> employees, Team_player team_player, Context context) {
        this.employees = employees;
        this.team_player = team_player;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_team_player,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.tv_employeename.setText(employees.get(position).getName());
       holder.tv_index.setText("#SL "+employees.get(position).getLevel());

       holder.itemView.setOnClickListener(v->{
           team_player.selected_player(employees.get(position),position);
       });

       holder.ll_detail_holder.setBackgroundColor(position%2==0?context.getResources().getColor(R.color.app_grey):context.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_employeename,tv_index;
        LinearLayout ll_detail_holder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_employeename=itemView.findViewById(R.id.tv_employeename);
            tv_index=itemView.findViewById(R.id.tv_index);
            ll_detail_holder=itemView.findViewById(R.id.ll_detail_holder);
        }
    }

    public interface Team_player{
        void selected_player(Employee employee,int pos);
    }
}
