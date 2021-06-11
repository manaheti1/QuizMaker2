package com.example.quizmaker2.Train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.Train;
import com.example.quizmaker2.database.models.User;

public class TrainResultActivity extends AppCompatActivity {
    TextView result;
    Button back;
    User user;
    Quiz quiz;
    Train train;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_result);
        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        quiz=(Quiz)intent.getSerializableExtra("quiz");
        train=(Train) intent.getSerializableExtra("train");
        result=findViewById(R.id.textView2);
        result.setText(train.getCorrect()+"/"+train.getTotal());
        back=findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrainResultActivity.this, QuizMainActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("quiz",quiz);
                finish();
                startActivity(intent);
            }
        });
    }
}