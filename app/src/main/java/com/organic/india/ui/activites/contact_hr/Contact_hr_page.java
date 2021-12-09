package com.organic.india.ui.activites.contact_hr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.organic.india.R;

public class Contact_hr_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_hr_page);
    }

    public void go_back(View view){
        onBackPressed();
    }
}