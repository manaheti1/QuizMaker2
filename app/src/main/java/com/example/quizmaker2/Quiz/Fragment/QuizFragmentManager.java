package com.example.quizmaker2.Quiz.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quizmaker2.Quiz.Fragment.Main.QuizMainFragment;
import com.example.quizmaker2.Quiz.Fragment.Record.StatisticFragment;

public class QuizFragmentManager extends FragmentStatePagerAdapter {
    int num=4;

    public QuizFragmentManager(@NonNull  FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new QuizMainFragment();
            case 1: return new TestFragment();
            case 2: return new StatisticFragment();
            case 3: return new SettingFragment();
            default: return new QuizMainFragment();
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
