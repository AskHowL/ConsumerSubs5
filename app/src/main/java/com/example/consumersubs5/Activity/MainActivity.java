package com.example.consumersubs5.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.consumersubs5.Adapter.ViewPagerAdapter;
import com.example.consumersubs5.Fragment.FragmentMovie;
import com.example.consumersubs5.Fragment.FragmentTV;
import com.example.consumersubs5.R;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity  {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddMovieFragment(new FragmentMovie(),getString(R.string.tab_text_1));
        adapter.AddMovieFragment(new FragmentTV(),getString(R.string.tab_text_2));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
