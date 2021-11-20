package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.organic.india.R;
import com.organic.india.pojo.leave_category.Leave_category;
import com.organic.india.pojo.pending_leave.Data;

import java.util.List;

public class Leave_type_spinner_adapter extends BaseAdapter {

    Context context;
    List<Data> types;
    LayoutInflater inflter;

    public Leave_type_spinner_adapter(Context applicationContext, List<Data> types ) {
        this.context = applicationContext;
        this.types = types;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount(){
        return types.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view = inflter.inflate(R.layout.row_common_spinner_card, null);
        TextView names = (TextView) view.findViewById(R.id.name);
        names.setText(types.get(i).getLeaveCategory());
        return view;
    }
}
