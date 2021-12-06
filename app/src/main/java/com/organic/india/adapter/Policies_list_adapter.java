package com.organic.india.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.async.Download_file;
import com.organic.india.pojo.policy.Policy;
import com.organic.india.ui.activites.webview.Webview_page;

import java.util.List;

public class Policies_list_adapter extends RecyclerView.Adapter<Policies_list_adapter.ViewHolder> {

    List<Policy> data;
    Activity context;
    View_pdf view_pdf;

    public Policies_list_adapter(List<Policy> data, Activity context, View_pdf view_pdf) {
        this.data = data;
        this.context = context;
        this.view_pdf = view_pdf;
    }

    @NonNull
    @Override
    public Policies_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_policy,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Policies_list_adapter.ViewHolder holder, int position) {

        holder.tv_index.setText(""+data.get(position).getSlNo());
        holder.tv_caption.setText(""+data.get(position).getName());
        holder.tv_addedby.setText(""+data.get(position).getDescription());
        holder.tv_updated_at.setText(""+data.get(position).getCreatedAt());

        holder.iv_view.setOnClickListener(v->{

            Log.e("pdf_link",""+data.get(position).getPdf());

            new Download_file(new Download_file.Download_result() {
                @Override
                public void getPath(String path) {

                    context.startActivity(new Intent(context, Webview_page.class)
                   .putExtra("pdf_link",""+path));

                    Log.e("pdf_link","downloaded path "+path);
                }

                @Override
                public void failed(String cause) {
                    Log.e("pdf_link","failed "+cause);
                }
            },context).execute(data.get(position).getPdf());

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_index,tv_caption,tv_addedby,tv_updated_at;
        ImageView iv_view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_index=itemView.findViewById(R.id.tv_index);
            tv_caption=itemView.findViewById(R.id.tv_caption);
            tv_addedby=itemView.findViewById(R.id.tv_addedby);
            tv_updated_at=itemView.findViewById(R.id.tv_updated_at);
            iv_view=itemView.findViewById(R.id.iv_view);
        }
    }


    public interface View_pdf{
        void url(String url);
    }
}
