package com.example.quizmaker2.Main.Fragment.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizmaker2.Main.Main;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.User;

public class AddQuiz extends AppCompatActivity {
    EditText name,num,des,time;
    Button Back,Create;
    SQLHelper sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
        name=findViewById(R.id.quizname);
        sql=new SQLHelper(this);
        time=findViewById(R.id.Quiztime);
        des=findViewById(R.id.description);
        Intent x=getIntent();
        User user=(User)x.getSerializableExtra("user");
        num=findViewById(R.id.questnum);
        Back=findViewById(R.id.back);
        Create=findViewById(R.id.create);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddQuiz.this, Main.class);
                intent.putExtra("user",user);
                finish();
                startActivity(intent);
            }
        });
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sql.addQuiz();
                String Fname=name.getText().toString();
                int Fuserid=user.getId();
                int Ftime=Integer.parseInt(time.getText().toString());
                String Fdes=des.getText().toString();
                int Fquestpertest=Integer.parseInt(num.getText().toString());
                String Fstate="Private";
                Quiz quiz=new Quiz(Fname,Fuserid,Fdes,Ftime,Fquestpertest,Fstate);
                sql.createQuiz(quiz);
                Intent intent=new Intent(AddQuiz.this, Main.class);
                intent.putExtra("user",user);
                finish();
                startActivity(intent);
            }
        });
    }
}