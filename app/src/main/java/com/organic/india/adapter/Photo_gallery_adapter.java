package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.organic.india.R;
import com.organic.india.pojo.dashboard.Image;
import java.util.List;

public class Photo_gallery_adapter extends RecyclerView.Adapter<Photo_gallery_adapter.ViewHolder> {

    List<Image> data;
    Context context;
    Pic_view pic_view;

    public Photo_gallery_adapter(List<Image> data, Context context, Pic_view pic_view) {
        this.data = data;
        this.context = context;
        this.pic_view = pic_view;
    }

    @NonNull
    @Override
    public Photo_gallery_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photo_gallery,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Photo_gallery_adapter.ViewHolder holder, int position) {

        Glide.with(context).load(data.get(position).getImg())
                .placeholder(R.drawable.image_placeholder).apply(new RequestOptions()).into(holder.iv_gallery);

        holder.itemView.setOnClickListener(v->{
            pic_view.pic(position,data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_gallery;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_gallery=itemView.findViewById(R.id.iv_gallery);
        }
    }

    public interface Pic_view{
        void pic(int pos,Image image);
    }
}
