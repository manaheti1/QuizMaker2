package com.example.quizmaker2.Main.Fragment.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker2.Main.Fragment.Home.HomeRecycleViewAdapter;
import com.example.quizmaker2.Main.Main;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.models.User;

import java.util.ArrayList;
import java.util.List;

public class SocialRecycleViewAdapter extends RecyclerView.Adapter<SocialRecycleViewAdapter.UserViewHolder>{
    List<User> list=new ArrayList<>();
    Main context;

    public Context getContext() {
        return context;
    }

    public void setContext(Main context) {
        this.context = context;
    }

    public SocialRecycleViewAdapter() {

    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);
        return new SocialRecycleViewAdapter.UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialRecycleViewAdapter.UserViewHolder holder, int position) {
        User user = list.get(position);
        if(user!=null)
        {

            holder.name.setText(user.getUsername());
            if (context.getSql().isFollow(context.getUser(),user))
                holder.follow.setChecked(true);
            else
                holder.follow.setChecked(false);
            holder.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.follow.isChecked()){
                        context.getSql().Follow(context.getUser(),user);
                    }else{
                        context.getSql().UnFollow(context.getUser(),user);
                    }
                }
            });
            holder.count.setText("Quiz: "+context.getSql().CountQuiz(user));
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

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView name,count;
        CheckBox follow;
        HomeRecycleViewAdapter.ItemClickListener itemClickListener;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Quizname);
            follow=itemView.findViewById(R.id.follow);
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
