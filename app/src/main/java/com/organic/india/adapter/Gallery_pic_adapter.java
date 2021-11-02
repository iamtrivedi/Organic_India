package com.organic.india.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.organic.india.R;
import com.organic.india.pojo.gallery_image_pojo.Data;
import java.util.ArrayList;
import java.util.List;

public class Gallery_pic_adapter extends RecyclerView.Adapter<Gallery_pic_adapter.ViewHolder> {

    List<Data> data = new ArrayList<>();
    Context context;
    View_pic view_pic;

    public Gallery_pic_adapter(List<Data> data, Context context, View_pic view_pic) {
        this.data = data;
        this.context = context;
        this.view_pic = view_pic;
    }

    @Override
    public Gallery_pic_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gallery_pic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Gallery_pic_adapter.ViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(data.get(position).getFileName())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.iv_gallery.setImageBitmap(resource);
                        holder.pb_progress.setVisibility(View.GONE);
                    }
                });

        holder.itemView.setOnClickListener(v->{
            view_pic.index(position);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pb_progress;
        ImageView iv_gallery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_gallery=itemView.findViewById(R.id.iv_gallery);
            pb_progress=itemView.findViewById(R.id.pb_progress);
        }
    }

    public interface View_pic{
        void index(int index);
    }
}
