package com.organic.india.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Image_slider_adapter extends FragmentPagerAdapter {

    private final List<Fragment> mList = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();

    public Image_slider_adapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }
    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    public void addFragment(Fragment fragment, String title) {
        mList.add(fragment);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
