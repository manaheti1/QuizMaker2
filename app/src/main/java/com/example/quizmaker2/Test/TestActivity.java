package com.example.quizmaker2.Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.quizmaker2.R;
import com.example.quizmaker2.Train.TrainActivity;
import com.example.quizmaker2.Train.TrainResultActivity;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.Answer;
import com.example.quizmaker2.database.models.Question;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.Train;
import com.example.quizmaker2.database.models.User;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class TestActivity extends AppCompatActivity {
    TextView time,count,context;
    RecyclerView recyclerView;
    TestRecycleAdapter adapter;
    User user;
    Quiz quiz;
    List<Question> questions;
    Train train;
    int total;
    int attemp=0;
    CountDownTimer timer;
    SQLHelper sql=new SQLHelper(this);
    int correct=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        adapter=new TestRecycleAdapter();
        context=findViewById(R.id.context);
        count=findViewById(R.id.Quizcount);
        time=findViewById(R.id.Quiztime);
        recyclerView=findViewById (R.id.ansList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        quiz=(Quiz)intent.getSerializableExtra("quiz");
        train=(Train)intent.getSerializableExtra("train");
        questions=train.getQuestionList();
        total=train.getTotal();
        adapter.setContext(this);
        load();
    }
    public void load(){
        if (questions.isEmpty()){
            Intent intent=new Intent(TestActivity.this, ResultActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("quiz",quiz);
            train.setCorrect(correct);
            intent.putExtra("train",train);
            finish();
            startActivity(intent);
        }else {
             timer=new CountDownTimer(quiz.getTime()*1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    time.setText(""+millisUntilFinished / 1000);
                }

                public void onFinish() {
                    load();
                }
            }.start();
            count.setText("Correct: " + correct + "   " + attemp + "/" + total);
            Question a = questions.get(0);
            questions.remove(0);
            context.setText(a.getContent());
            List<Answer> listA = sql.getAnswerByQuestion(a);
            adapter.setList(listA);
            adapter.notifyDataSetChanged();
            attemp++;
        }


    }
    public void correct(){
        correct++;
    }
}