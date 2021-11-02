package com.organic.india.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.pojo.gallery_folder.Data;
import com.organic.india.pojo.gallery_image_pojo.Gallery_image;
import com.organic.india.ui.activites.gallery_image.Gallery_image_page;

import java.util.List;

public class Gallery_folder_adapter extends RecyclerView.Adapter<Gallery_folder_adapter.ViewHolder> {

    List<Data> data;
    Context context;

    public Gallery_folder_adapter(List<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Gallery_folder_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gallery_folder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Gallery_folder_adapter.ViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getFolderName());

        holder.itemView.setOnClickListener(v->{
            context.startActivity(new Intent(context, Gallery_image_page.class)
            .putExtra("folder_id",""+data.get(position).getFolderId())
            .putExtra("title",""+data.get(position).getFolderName()));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        LinearLayout ll_folder_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_folder_card=itemView.findViewById(R.id.ll_folder_card);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}
