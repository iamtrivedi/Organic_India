package com.organic.india.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.pojo.whoswho.Who;
import com.organic.india.ui.activites.whose_department_page.Whose_department_page;

import java.util.List;

public class Whoose_who_adapter extends RecyclerView.Adapter<Whoose_who_adapter.ViewHolder> {

    List<Who> data;
    Context context;

    public Whoose_who_adapter(List<Who> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Whoose_who_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_whosewho,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getTitle());
        holder.itemView.setOnClickListener(v->{
          context.startActivity(new Intent(context, Whose_department_page.class)
          .putExtra("ID",""+data.get(position).getDepartmentId())
          .putExtra("TITLE",""+data.get(position).getTitle()));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}
