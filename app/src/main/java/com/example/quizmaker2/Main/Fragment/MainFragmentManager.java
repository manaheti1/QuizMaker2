package com.example.quizmaker2.Main.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quizmaker2.Main.Fragment.Account.SocialFragment;
import com.example.quizmaker2.Main.Fragment.Home.HomeFragment;

public class MainFragmentManager extends FragmentStatePagerAdapter {
    int num=3;

    public MainFragmentManager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new SocialFragment();
            case 2: return new AccountFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
