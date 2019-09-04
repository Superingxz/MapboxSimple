package com.softgarden.baselibrary.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yatoooon on 2018/6/12.
 */

public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments = null;
    private Context context;
    private ArrayList<String> titles;

    public BaseViewPagerAdapter(Context context, FragmentManager fm, List<BaseFragment> fragments, ArrayList<String> titles) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
    }

    public BaseViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }



    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && position < titles.size()) {
            return titles.get(position);
        }
        return super.getPageTitle(position);
    }

    public void setTitle(ArrayList<String> orderMenu) {
        this.titles = orderMenu;
    }
}