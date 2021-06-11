package com.example.quizmaker2.Quiz.AddQuiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.Answer;
import com.example.quizmaker2.database.models.Question;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.User;

import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AnswerRecycleAdapter adapter;
    EditText context;
    SQLHelper sql=new SQLHelper(this);
    Button add,done,del;
    User user;
    String method;
    Quiz quiz;
    Question quest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        context=findViewById(R.id.context);
        recyclerView=findViewById(R.id.ansList);
        adapter=new AnswerRecycleAdapter();
        adapter.setContext(this);
        Intent x=getIntent();
        quiz=(Quiz)x.getSerializableExtra("quiz");
        del=findViewById(R.id.delete);
        user=(User)x.getSerializableExtra("user");
        add=findViewById(R.id.addAnsw);
        done=findViewById(R.id.done);
        method=x.getStringExtra("method");
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        if (user.getId()!=quiz.getUserId()){
            context.setFocusable(false);

            add.setEnabled(false);
            done.setEnabled(false);
            del.setEnabled(false);
        }
        if (method.equals("PUT")){
            quest=(Question)x.getSerializableExtra("question");
            List<Answer> anslist=sql.getAnswerByQuestion(quest);
            context.setText(quest.getContent());
            adapter.setList(anslist);
            adapter.notifyDataSetChanged();
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sql.delQuestion((quest));
                    Intent del=new Intent(AddQuestionActivity.this,QuizMainActivity.class);
                    del.putExtra("user",user);
                    del.putExtra("quiz",quiz);
                    finish();
                    startActivity(del);
                }
            });
        }else{
            del.setEnabled(false);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context.getText().toString().trim().equals("")||!checkAdapter()){
                    Toast.makeText(AddQuestionActivity.this,"Question is not allowed to be null",Toast.LENGTH_LONG).show();
                }else
                if (method.equals("POST")){
                    Question question=new Question(1,context.getText().toString(),quiz.getId());
                    long cc= sql.addQuestion(question);
                    List<Answer> list1=adapter.getList();
                    for (int i=0;i<list1.size();i++){
                        list1.get(i).setQuestionid((int) cc);
                        sql.addAnswer(list1.get(i));
                    }
                    Intent intent=new Intent(AddQuestionActivity.this, QuizMainActivity.class);
                    intent.putExtra("quiz",quiz);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }else{
                    sql.editQuestion(quest);
                    List<Answer> list1=adapter.getList();
                    for (int i=0;i<list1.size();i++){
                        list1.get(i).setQuestionid((int) quest.getId());
                        sql.addAnswer(list1.get(i));
                    }
                    Intent intent=new Intent(AddQuestionActivity.this, QuizMainActivity.class);
                    intent.putExtra("quiz",quiz);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }

            }
        });

    }

    public boolean checkAdapter(){
        List<Answer> a=adapter.getList();
        for (int i=0;i<a.size();i++){
            if (a.get(i).isTrue()) return true;

        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, QuizMainActivity.class);
        intent.putExtra("quiz",quiz);
        intent.putExtra("user",user);
        startActivity(intent);
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

    public Question getQuest() {
        return quest;
    }

    public void setQuest(Question quest) {
        this.quest = quest;
    }
}