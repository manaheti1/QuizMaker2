package com.example.quizmaker2.Quiz.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.Test.TestActivity;
import com.example.quizmaker2.Train.TrainActivity;
import com.example.quizmaker2.database.models.Question;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.Train;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {
    TextView time,count,total;
    EditText questionpertrain;
    Button Test,Train;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QuizMainActivity activity=(QuizMainActivity)getActivity();
        count=activity.findViewById(R.id.testnum);
        time=activity.findViewById(R.id.Quiztime);
        total=activity.findViewById(R.id.total);
        Test=activity.findViewById(R.id.testing);
        questionpertrain=activity.findViewById(R.id.questionpertrain);
        Train=activity.findViewById(R.id.training);

        total.setText("Total Questions: "+Integer.toString(activity.getSql().getQuestionCountByQuiz(activity.getQuiz())));
        count.setText("Question per test :"+Integer.toString(activity.getQuiz().getQuestionpertest()));
        time.setText("Time per question :"+Integer.toString(activity.getQuiz().getTime()));

        Train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, TrainActivity.class);
                List<Question> lq=generateTrain(activity.getQuiz());
                com.example.quizmaker2.database.models.Train train=new Train(lq,0,lq.size());
                intent.putExtra("quiz",activity.getQuiz());
                intent.putExtra("user",activity.getUser());
                intent.putExtra("train",train);
                ((Activity)v.getContext()).finish();
                startActivity(intent);
            }
        });
        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.getSql().getQuestionCountByQuiz(activity.getQuiz())<activity.getQuiz().getQuestionpertest()){
                    Toast.makeText(activity,"Not enought questions",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(activity, TestActivity.class);
                    List<Question> lq = generateTrain(activity.getQuiz());
                    com.example.quizmaker2.database.models.Train train = new Train(lq, 0, lq.size());
                    intent.putExtra("quiz", activity.getQuiz());
                    intent.putExtra("user", activity.getUser());
                    intent.putExtra("train", train);
                    ((Activity) v.getContext()).finish();
                    startActivity(intent);
                }
            }
        });


    }
    public List<Question> generateTest(Quiz q){
        QuizMainActivity activity=(QuizMainActivity)getActivity();
        List<Question> listq=activity.getSql().getQuestionByQuiz(q);
        Collections.shuffle(listq);
        while(listq.size()>activity.getQuiz().getQuestionpertest()){
            listq.remove(listq.size()-1);
        }
        return listq;
    }
    public List<Question> generateTrain(Quiz q){
        QuizMainActivity activity=(QuizMainActivity)getActivity();

        List<Question> listq=new ArrayList<>();
        int amount;
        if (questionpertrain.getText().toString().trim().equals("")){
            amount=activity.getQuiz().getQuestionpertest();
        }else{
            amount=Integer.parseInt(questionpertrain.getText().toString());
        }


        while(listq.size()<amount){
            List<Question> temp=activity.getSql().getQuestionByQuiz(q);
            listq.addAll(temp);
        }
        Collections.shuffle(listq);
        while(listq.size()>amount){
            listq.remove(listq.size()-1);
        }
        return listq;
    }
}