package com.organic.india.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import com.organic.india.pojo.dashboard.Pdf;
import java.util.List;

public class Press_coverage_adapter extends RecyclerView.Adapter<Press_coverage_adapter.ViewHolder> {

    Context context;
    List<Pdf> data;

    public Press_coverage_adapter(Context context, List<Pdf> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Press_coverage_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_press_coverage,parent,false);
        return new Press_coverage_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Press_coverage_adapter.ViewHolder holder, int position) {
     holder.tv_index.setText(""+(position+1));
     holder.tv_addedby.setText(data.get(position).getAddedBy());
     holder.tv_caption.setText(data.get(position).getCaption());
     holder.tv_updated_at.setText(data.get(position).getLastUpdatedAt());
     holder.iv_view.setOnClickListener(v->{

         Intent browserIntent = new Intent(Intent.ACTION_VIEW);
         browserIntent.setDataAndType(Uri.parse(data.get(position).getPdf()), "application/pdf");
         Intent chooser = Intent.createChooser(browserIntent,"Browse via");
         chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
         context.startActivity(chooser);

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
}
