package com.example.consumersubs5.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> movieFragment = new ArrayList<>();
    private final List<String> movieTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return movieFragment.get(i);
    }

    @Override
    public int getCount() {
        return movieTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return movieTitle.get(position);
    }

    public void AddMovieFragment(Fragment fragment,String title){
        movieFragment.add(fragment);
        movieTitle.add(title);
    }
}
