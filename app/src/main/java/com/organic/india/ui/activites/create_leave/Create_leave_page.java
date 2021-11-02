package com.organic.india.ui.activites.create_leave;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.organic.india.R;

public class Create_leave_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_leave_page);
    }

    public void back_press(View view){
        onBackPressed();
    }
}