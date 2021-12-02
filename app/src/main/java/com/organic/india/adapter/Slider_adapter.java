package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import java.util.List;

public class Slider_adapter extends RecyclerView.Adapter<Slider_adapter.ImageSwiper> {

    private List<Integer> data;
    private Context context;
    Selected_image selected_image;

    public Slider_adapter(List<Integer> data, Context context, Selected_image selected_image) {
        this.data = data;
        this.context = context;
        this.selected_image = selected_image;
    }

    @NonNull
    @Override
    public ImageSwiper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slidingimages,parent, false);

        return new ImageSwiper(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageSwiper holder, int position) {
        holder.imageView.setImageResource(data.get(position));
        holder.itemView.setOnClickListener(v->{
           selected_image.image(position);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImageSwiper extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageSwiper(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);

        }
    }

    public interface Selected_image{
        void image(int pos);
    }
}
