package com.example.quizmaker2.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizmaker2.Main.Main;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;
import com.example.quizmaker2.database.models.User;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button Register,Login;
    SQLHelper sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sql=new SQLHelper(this);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        Register=findViewById(R.id.register);
        Login=findViewById(R.id.login);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=sql.checkLogin(username.getText().toString(),password.getText().toString());
                if (user!=null){
                    Intent intent=new Intent(MainActivity.this, Main.class);
                    intent.putExtra("user",user);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(v.getContext(),"Wrong infomation",Toast.LENGTH_LONG).show();
                }

//                SQL.checklogin(username.getText().toString(),password.getText().toString());
            }
        });
    }
}