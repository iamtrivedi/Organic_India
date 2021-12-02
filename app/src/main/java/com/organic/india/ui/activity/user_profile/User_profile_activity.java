package com.organic.india.ui.activity.user_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.organic.india.R;
import com.organic.india.ui.fragments.user_profile.User_profile;

public class User_profile_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        loadFragment(new User_profile());
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