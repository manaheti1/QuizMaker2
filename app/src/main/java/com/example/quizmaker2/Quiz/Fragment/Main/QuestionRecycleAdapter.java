package com.example.quizmaker2.Quiz.Fragment.Main;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker2.Quiz.AddQuiz.AddQuestionActivity;
import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.models.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRecycleAdapter extends RecyclerView.Adapter<QuestionRecycleAdapter.QuestionViewHolder>{

    List<Question> list=new ArrayList<>();
    QuizMainActivity context;

    public QuizMainActivity getContext() {
        return context;
    }

    public void setContext(QuizMainActivity context) {
        this.context = context;
    }

    public List<Question> getList() {
        return list;
    }

    public void setList(List<Question> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card,parent,false);
        return new QuestionRecycleAdapter.QuestionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  QuestionRecycleAdapter.QuestionViewHolder holder, int position) {
        Question question = list.get(position);
        if(question!=null)
        {
            holder.context.setText(question.getContent().toString());
            //+sql.getUsernamebyId(quiz.getUserId())
//            holder.follow.setChecked(true);
            //+sql.getQuestionCountByQuizId(quiz.getId())
//            holder.count.setText("Quiz: ");
            holder.itemClickListener = new QuestionRecycleAdapter.ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(view.getContext(), AddQuestionActivity.class);
                    ((Activity)view.getContext()).finish();
                    intent.putExtra("method","PUT");
                    intent.putExtra("question",question);
                    intent.putExtra("quiz",context.getQuiz());
                    intent.putExtra("user",context.getUser());
                    view.getContext().startActivity(intent);
                }
            };
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface  ItemClickListener
    {
        public void onClick(View view , int position);
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{
        TextView context;
        ItemClickListener itemClickListener;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.findViewById(R.id.Quizname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(v,getAdapterPosition());
                }
            });
        }
    }

}
