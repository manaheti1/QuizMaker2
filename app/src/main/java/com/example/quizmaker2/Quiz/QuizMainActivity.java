package com.example.quizmaker2.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.quizmaker2.Main.Main;
import com.example.quizmaker2.Quiz.Fragment.QuizFragmentManager;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizMainActivity extends AppCompatActivity {
    QuizFragmentManager adapter;
    BottomNavigationView nav;
    SQLHelper sql;
    Quiz quiz;
    User user;
    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        quiz=(Quiz)intent.getSerializableExtra("quiz");
        sql=new SQLHelper(this);
        adapter=new QuizFragmentManager(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vp=findViewById(R.id.vp);
        nav=findViewById(R.id.nav);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: nav.getMenu().findItem(R.id.home).setChecked(true); break;
                    case 1: nav.getMenu().findItem(R.id.test).setChecked(true); break;
                    case 2: nav.getMenu().findItem(R.id.chart).setChecked(true); break;
                    case 3: nav.getMenu().findItem(R.id.setting).setChecked(true); break;
                    default:  nav.getMenu().findItem(R.id.home).setChecked(true); break;
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
                    case R.id.test: vp.setCurrentItem(1); break;
                    case R.id.chart: vp.setCurrentItem(2); break;
                    case R.id.setting: vp.setCurrentItem(3); break;
                    default: vp.setCurrentItem(0); break;

                }
                return true;
            }
        });
    }

    public SQLHelper getSql() {
        return sql;
    }

    public void setSql(SQLHelper sql) {
        this.sql = sql;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, Main.class);
        intent.putExtra("user",getUser());
        startActivity(intent);
    }
}