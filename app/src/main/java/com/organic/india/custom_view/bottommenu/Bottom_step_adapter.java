package com.organic.india.custom_view.bottommenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.organic.india.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Bottom_step_adapter extends RecyclerView.Adapter<Bottom_step_adapter.ViewHolder> {

    List<Bottom_step> data;
    Context context;
    Tap_menu tap_menu;

    int current_menu = -1;

    public Bottom_step_adapter(List<Bottom_step> data, Context context, Tap_menu tap_menu) {
        this.data = data;
        this.context = context;
        this.tap_menu = tap_menu;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registration_step,parent,false);
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      holder.iv_icon.setImageResource(data.get(position).getIcon_resource());
      holder.tv_title.setText(data.get(position).getName());

      holder.itemView.setOnClickListener(v->{
          indicate_menu(position);
          tap_menu.menu(data.get(position));
      });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tab)LinearLayout tab;
        @BindView(R.id.iv_icon)ImageView iv_icon;
        @BindView(R.id.tv_title)TextView tv_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface Tap_menu {
        void menu(Bottom_step menu);
    }

    public void indicate_menu(int current_menu){
        this.current_menu = current_menu;
        notifyDataSetChanged();
    }

}
