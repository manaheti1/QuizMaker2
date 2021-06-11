package com.example.quizmaker2.Test;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker2.R;
import com.example.quizmaker2.database.models.Answer;

import java.util.ArrayList;
import java.util.List;

public class TestRecycleAdapter extends RecyclerView.Adapter<TestRecycleAdapter.TestViewHolder> {
    List<Answer> list=new ArrayList<>();
    TestActivity context;
    public List<Answer> getList() {
        return list;
    }

    public void setList(List<Answer> list) {
        this.list = list;
    }

    public TestActivity getContext() {
        return context;
    }

    public void setContext(TestActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TestRecycleAdapter.TestViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_card,parent,false);
        return new TestRecycleAdapter.TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  TestRecycleAdapter.TestViewHolder holder, int position) {
        Answer answer = list.get(position);
        if(answer!=null)
        {
            holder.name.setText(answer.getContext());
            String x=""+(char)(position+'A');
            holder.number.setText(x);
            holder.itemClickListener = new TestRecycleAdapter.ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    context.timer.cancel();
                    if (answer.isTrue()) {
                        holder.name.setBackgroundResource(R.color.green);
                        Toast.makeText(view.getContext(), "Correct answer",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(view.getContext(), ResultActivity.class);
                        Handler handler = new Handler();
                        context.correct();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // yourMethod();
                                holder.name.setBackgroundResource(R.color.white);
                                context.load();
                            }
                        }, 500);

                    }else{
                        holder.name.setBackgroundResource(R.color.red);
                        Toast.makeText(view.getContext(), "Wrong answer",Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(view.getContext(), TestActivity.class);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // yourMethod();
                                holder.name.setBackgroundResource(R.color.white);
                                context.load();
                            }
                        }, 500);

                    }
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

    class TestViewHolder extends RecyclerView.ViewHolder{
        TextView name,number;
        ItemClickListener itemClickListener;
        public TestViewHolder(@NonNull  View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Quizname);
            number=itemView.findViewById(R.id.number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(itemView,getAdapterPosition());
                }
            });

        }
    }
}

