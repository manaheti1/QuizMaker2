package com.example.quizmaker2.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.quizmaker2.Main.Fragment.MainFragmentManager;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Main extends AppCompatActivity {
    ViewPager vp;
    User user;



    SQLHelper sql=new SQLHelper(this);
    BottomNavigationView nav;
    MainFragmentManager adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        user=(User) intent.getSerializableExtra("user");
        vp=findViewById(R.id.vp);
        nav=findViewById(R.id.nav);
        adapter=new MainFragmentManager(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: nav.getMenu().findItem(R.id.home).setChecked(true); break;
                    case 1: nav.getMenu().findItem(R.id.social).setChecked(true); break;
                    case 2: nav.getMenu().findItem(R.id.setting).setChecked(true); break;

                    default: nav.getMenu().findItem(R.id.home).setChecked(true); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home: vp.setCurrentItem(0); break;
                    case R.id.social: vp.setCurrentItem(1); break;
                    case R.id.setting: vp.setCurrentItem(2); break;
                    default: vp.setCurrentItem(0); break;
                }
                return true;
            }
        });
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SQLHelper getSql() {
        return sql;
    }

    public void setSql(SQLHelper sql) {
        this.sql = sql;
    }
}