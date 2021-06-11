package com.example.quizmaker2.Quiz.AddQuiz;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker2.Quiz.Fragment.Main.QuestionRecycleAdapter;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.models.Answer;
import com.example.quizmaker2.database.models.Question;

import java.util.ArrayList;
import java.util.List;

public class AnswerRecycleAdapter extends RecyclerView.Adapter<AnswerRecycleAdapter.AnswerViewHolder> {
    List<Answer> list=new ArrayList<>();
    int co=0;
    AddQuestionActivity context;

    public AddQuestionActivity getContext() {
        return context;
    }

    public void setContext(AddQuestionActivity context) {
        this.context = context;
    }

    public List<Answer> getList() {
        return list;
    }

    public void setList(List<Answer> list) {
        this.list = list;
    }

    public void add(){
        list.add(new Answer(list.size(),Integer.toString(co++),false));
        notifyDataSetChanged();
        System.out.println(list.size());
    }
    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_card,parent,false);
        return new AnswerRecycleAdapter.AnswerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  AnswerRecycleAdapter.AnswerViewHolder holder, int position) {
        Answer answer = list.get(position);
        if(answer!=null)
        {
            if (context.getQuiz().getUserId()!=context.getUser().getId()){
                holder.context.setFocusable(false);
                holder.isTrue.setEnabled(false);
                holder.del.setVisibility(View.GONE);
            }
            holder.context.setText(answer.getContext().toString());
            holder.context.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                    {
                        list.get(position).setContext(holder.context.getText().toString());
                        System.out.println("------"+position);
                        for (int i=0;i<list.size();i++){
                            System.out.println(list.get(i).getContext()+" ");
                        }
                        System.out.println("------");
                    }

                }
            });
            holder.isTrue.setChecked(answer.isTrue());
            holder.isTrue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if ( holder.isTrue.isChecked()){
                        for ( int i=0;i<list.size();i++){
                            list.get(i).setTrue(false);
                        }
                         list.get(position).setTrue(true);
                         notifyDataSetChanged();
                     }

                }
            });
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            //+sql.getUsernamebyId(quiz.getUserId())
//            holder.follow.setChecked(true);
            //+sql.getQuestionCountByQuizId(quiz.getId())
//            holder.count.setText("Quiz: ");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AnswerViewHolder extends RecyclerView.ViewHolder{
        CheckBox isTrue;
        EditText context;
        Button del;
        public AnswerViewHolder(@NonNull  View itemView) {
            super(itemView);
            isTrue=itemView.findViewById(R.id.isTrue);
            context=itemView.findViewById(R.id.context);
            del=itemView.findViewById(R.id.del);
        }
    }
}
