package com.example.quizmaker2.Train;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.Answer;
import com.example.quizmaker2.database.models.Question;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.Train;
import com.example.quizmaker2.database.models.User;

import java.util.ArrayList;
import java.util.List;

public class TrainActivity extends AppCompatActivity {
    TextView content,count;
    TrainRecycleAdapter adapter;
    RecyclerView recyclerView;
    User user;
    Quiz quiz;
    Train train;
    List<Question> questions;
    int total;
    int attemp=0;
    SQLHelper sql=new SQLHelper(this);
    int correct=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        adapter=new TrainRecycleAdapter();
        content=findViewById(R.id.content);
        count=findViewById(R.id.Quizcount1);
        recyclerView=findViewById(R.id.ansList);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        adapter.setContext(this);

        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        quiz=(Quiz)intent.getSerializableExtra("quiz");
        train=(Train)intent.getSerializableExtra("train");
        questions=train.getQuestionList();
        total=train.getTotal();
        load();

    }

    public void load(){
        if (questions.isEmpty()){
            Intent intent=new Intent(TrainActivity.this,TrainResultActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("quiz",quiz);
            train.setCorrect(correct);
            intent.putExtra("train",train);
            finish();
            startActivity(intent);
        }else {
            count.setText("Correct: " + correct + "   " + attemp + "/" + total);
            Question a = questions.get(0);
            questions.remove(0);
            content.setText(a.getContent());
            List<Answer> listA = sql.getAnswerByQuestion(a);
            adapter.setList(listA);
            adapter.notifyDataSetChanged();
            attemp++;
        }

    }

    public void correct(){
        correct++;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(TrainActivity.this, QuizMainActivity.class);
        intent.putExtra("user",user);
        intent.putExtra("quiz",quiz);
        finish();
        startActivity(intent);
    }
}