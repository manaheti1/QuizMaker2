package com.example.quizmaker2.Train;

import android.annotation.SuppressLint;
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
import com.example.quizmaker2.Test.ResultActivity;
import com.example.quizmaker2.database.models.Answer;
import com.example.quizmaker2.database.models.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainRecycleAdapter extends RecyclerView.Adapter<TrainRecycleAdapter.TrainViewHolder> {
    List<Answer> list=new ArrayList<>();
    TrainRecycleAdapter.TrainViewHolder correct;
    TrainActivity context;

    public List<Answer> getList() {
        return list;
    }

    public void setList(List<Answer> list) {
        this.list = list;
    }

    public TrainActivity getContext() {
        return context;
    }

    public void setContext(TrainActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_card,parent,false);
        return new TrainRecycleAdapter.TrainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  TrainRecycleAdapter.TrainViewHolder holder, int position) {
        Answer answer = list.get(position);
        if(answer!=null)
        {
            holder.name.setText(answer.getContext());
            String x=""+(char)(position+'A');
            holder.number.setText(x);
            if (answer.isTrue()) correct=holder;
            holder.itemClickListener = new TrainRecycleAdapter.ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if (answer.isTrue()) {

                        holder.name.setBackgroundResource(R.color.green);
                        Toast.makeText(view.getContext(), "Correct answer",Toast.LENGTH_LONG).show();
                        context.correct();
                        Intent intent = new Intent(view.getContext(), ResultActivity.class);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                holder.name.setBackgroundResource(R.color.white);
                                context.load();
                            }
                        }, 500);

                    }else{
                        holder.name.setBackgroundResource(R.color.red);
                        Toast.makeText(view.getContext(), "Wrong answer",Toast.LENGTH_LONG).show();
                        correct.name.setBackgroundResource(R.color.green);
                        Intent intent = new Intent(view.getContext(), TrainActivity.class);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                holder.name.setBackgroundResource(R.color.white);
                                correct.name.setBackgroundResource(R.color.white);
                                // yourMethod();
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

    class TrainViewHolder extends RecyclerView.ViewHolder{
        TextView name,number;
        ItemClickListener itemClickListener;
        public TrainViewHolder(@NonNull  View itemView) {
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
