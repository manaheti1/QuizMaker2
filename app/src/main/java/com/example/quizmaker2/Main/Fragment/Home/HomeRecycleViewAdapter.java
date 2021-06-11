package com.example.quizmaker2.Main.Fragment.Home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker2.Main.Main;
import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.models.Quiz;

import java.util.ArrayList;
import java.util.List;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.QuizViewHolder>{
    List<Quiz> list=new ArrayList<>();
    Main context;

    public Main getContext() {
        return context;
    }

    public void setContext(Main context) {
        this.context = context;
    }

    public List<Quiz> getList() {
        return list;
    }

    public void setList(List<Quiz> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_card,parent,false);
        return new HomeRecycleViewAdapter.QuizViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecycleViewAdapter.QuizViewHolder holder, int position) {
        Quiz quiz = list.get(position);
        if(quiz!=null)
        {
            holder.name.setText(quiz.getName());
            //+sql.getUsernamebyId(quiz.getUserId())
            holder.createdBy.setText("Created by: "+context.getSql().getUserNameByQuiz(quiz));
            //+sql.getQuestionCountByQuizId(quiz.getId())
            holder.count.setText("Questions: "+context.getSql().getQuestionCountByQuiz(quiz));
            holder.itemClickListener = new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(view.getContext(), QuizMainActivity.class);
                    intent.putExtra("quiz",quiz);
                    intent.putExtra("user",context.getUser());
                    ((Activity)view.getContext()).finish();
                    view.getContext().startActivity(intent);
                }
            };
        }
    }

    public interface  ItemClickListener
    {
        public void onClick(View view , int position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder{
        TextView name,createdBy,count;
        ItemClickListener itemClickListener;
        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Quizname);
            createdBy=itemView.findViewById(R.id.createdBy);
            count=itemView.findViewById(R.id.Quizcount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(v,getAdapterPosition());
                }
            });

        }
    }
}
