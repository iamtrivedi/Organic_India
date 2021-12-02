package com.organic.india.ui.activity.change_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.organic.india.R;
import com.organic.india.ui.fragments.change_password.Change_password;

public class Change_password_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        loadFragment(new Change_password(new Change_password.Actions() {
            @Override
            public void on_update() {

            }
            @Override
            public void on_cancel() {
                onBackPressed();
            }
        }));
    }

    private void loadFragment(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, fragment, fragmentTag);
            transaction.commit();
        }
    }
}