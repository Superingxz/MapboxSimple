package com.softgarden.baselibrary.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * FragmentStatePagerAdapter 基类 配合ViewPager使用 简单快捷
 */
public class FragmentBaseSimplePagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments;
    private String[] titles;

    public FragmentBaseSimplePagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments[position];
        CharSequence pageTitle = getPageTitle(position);
        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(View container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && position < titles.length) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }

    public void setTitle(String[] orderMenu) {
        this.titles = orderMenu;
    }
}