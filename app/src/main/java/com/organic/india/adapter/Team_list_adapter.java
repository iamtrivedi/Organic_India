package com.organic.india.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.pojo.team_listing.Employee;

import java.util.List;

public class Team_list_adapter extends RecyclerView.Adapter<Team_list_adapter.ViewHolder> {

    List<Employee> employees;
    Team_player team_player;

    public Team_list_adapter(List<Employee> employees, Team_player team_player) {
        this.employees = employees;
        this.team_player = team_player;
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
           team_player.selected_player(employees.get(position));
       });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_employeename,tv_index;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_employeename=itemView.findViewById(R.id.tv_employeename);
            tv_index=itemView.findViewById(R.id.tv_index);
        }
    }

    public interface Team_player{
        void selected_player(Employee employee);
    }
}
