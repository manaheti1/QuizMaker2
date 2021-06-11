package com.example.quizmaker2.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.Record;
import com.example.quizmaker2.database.models.Train;
import com.example.quizmaker2.database.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
    TextView result;
    Button back;
    User user;
    Quiz quiz;
    Train train;
    SQLHelper sql=new SQLHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        back=findViewById(R.id.button5);
        quiz=(Quiz)intent.getSerializableExtra("quiz");
        train=(Train) intent.getSerializableExtra("train");
        result=findViewById(R.id.textView);
        result.setText(train.getCorrect()+"/"+train.getTotal());
        Date currentTime = Calendar.getInstance().getTime();
        Record record=new Record(0,user.getId(),quiz.getId(),currentTime,train.getPercent());
        sql.addRecord(record);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ResultActivity.this, QuizMainActivity.class);
                intent1.putExtra("user",user);
                intent1.putExtra("quiz",quiz);
                finish();
                startActivity(intent1);
            }
        });
    }
}