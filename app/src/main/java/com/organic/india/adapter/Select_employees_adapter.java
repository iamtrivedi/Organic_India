package com.organic.india.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.organic.india.R;
import com.organic.india.pojo.team_listing.Employee;
import java.util.List;

public class Select_employees_adapter extends BaseAdapter {

    Context context;
    List<Employee> employees;
    LayoutInflater inflter;

    public Select_employees_adapter(Context applicationContext, List<Employee> employees ) {
        this.context = applicationContext;
        this.employees = employees;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount(){
        return employees.size();
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
        view = inflter.inflate(R.layout.row_select_employee, null);
        TextView names = (TextView) view.findViewById(R.id.name);
        names.setText(employees.get(i).getName());
        return view;
    }
}
