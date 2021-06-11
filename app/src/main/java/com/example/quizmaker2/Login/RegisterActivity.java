package com.example.quizmaker2.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizmaker2.R;
import com.example.quizmaker2.database.SQLHelper;

public class RegisterActivity extends AppCompatActivity {
    Button Back,Register;
    SQLHelper sql;
    EditText username,password,confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Back=findViewById(R.id.back);
        Register=findViewById(R.id.register);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        sql=new SQLHelper(this);
        confirmpassword=findViewById(R.id.confirmpassword);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(confirmpassword.getText().toString())){
                    sql.register(username.getText().toString(),password.getText().toString());
                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Confirm password doesn't match",Toast.LENGTH_LONG).show();
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}