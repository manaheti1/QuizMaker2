package com.example.quizmaker2.Quiz.Fragment.Record;

import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.Train.TrainRecycleAdapter;
import com.example.quizmaker2.database.models.Answer;
import com.example.quizmaker2.database.models.Record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecordRecycleAdapter extends RecyclerView.Adapter<RecordRecycleAdapter.RecordViewHolder> {
    List<Record> list=new ArrayList<>();
    QuizMainActivity context;

    public List<Record> getList() {
        return list;
    }

    public QuizMainActivity getContext() {
        return context;
    }

    public void setContext(QuizMainActivity context) {
        this.context = context;
    }

    public void setList(List<Record> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_card,parent,false);
        return new RecordRecycleAdapter.RecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecordRecycleAdapter.RecordViewHolder holder, int position) {
        Record record = list.get(position);
        if(record!=null)
        {
           holder.name.setText(context.getSql().getUsernamebyId(record.getUserid()));
           holder.correct.setText(Double.toString(record.getPercent()));
           if (record.getPercent()>=80){
               holder.itemView.setBackgroundResource(android.R.color.holo_green_light);
           }else if (record.getPercent()>=50){
               holder.itemView.setBackgroundResource(android.R.color.holo_blue_dark);
           }else{
               holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
           }
           DateFormat dateFormat = new SimpleDateFormat("hh:mm yyyy-MM-dd ");
           holder.time.setText(dateFormat.format(record.getTimestamp()));
           if (context.getUser().getId()!=context.getQuiz().getUserId()){
               holder.remove.setVisibility(View.GONE);
           }
           holder.remove.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   context.getSql().deleteRecord(record);
                   list.remove(position); notifyDataSetChanged();
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder{
        TextView name,time,correct;
        ImageButton remove;
        public RecordViewHolder(@NonNull  View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.doneby);
            time=itemView.findViewById(R.id.timedone);
            correct=itemView.findViewById(R.id.correct);
            remove=itemView.findViewById(R.id.remove);
        }
    }
}
