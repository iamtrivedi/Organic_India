package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.organic.india.R;
import com.organic.india.pojo.whoswhoemployee.Employee;
import java.util.List;

public class Whoosewhoemployee_adapter extends RecyclerView.Adapter<Whoosewhoemployee_adapter.ViewHolder> {

    List<Employee> data;
    Context context;

    public Whoosewhoemployee_adapter(List<Employee> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Whoosewhoemployee_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_whosewho_employee,parent,false);
        return new Whoosewhoemployee_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Whoosewhoemployee_adapter.ViewHolder holder, int position) {

       holder.tv_name.setText(data.get(position).getName());
       holder.tv_designation.setText(data.get(position).getDesignation());
       holder.tv_contact.setText(data.get(position).getMobile());
       holder.tv_email.setText(data.get(position).getEmail());
       holder.tv_department.setText(data.get(position).getDepartment());

        Glide.with(context).load(data.get(position).getImg())
                .placeholder(R.drawable.image_placeholder).apply(new RequestOptions()).into(holder.iv_pic);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView iv_pic;
        TextView tv_name,tv_designation,tv_contact,tv_email,tv_department;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_pic=itemView.findViewById(R.id.iv_pic);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_designation=itemView.findViewById(R.id.tv_designation);
            tv_contact=itemView.findViewById(R.id.tv_contact);
            tv_email=itemView.findViewById(R.id.tv_email);
            tv_department=itemView.findViewById(R.id.tv_department);
        }
    }
}
