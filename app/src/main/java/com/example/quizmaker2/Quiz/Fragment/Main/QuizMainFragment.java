package com.example.quizmaker2.Quiz.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizmaker2.Quiz.AddQuiz.AddQuestionActivity;
import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.database.models.Question;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizMainFragment extends Fragment {
    RecyclerView recyclerView;
    QuestionRecycleAdapter adapter;
    FloatingActionButton add;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizMainFragment newInstance(String param1, String param2) {
        QuizMainFragment fragment = new QuizMainFragment();
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
        return inflater.inflate(R.layout.fragment_quiz_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new QuestionRecycleAdapter();
        QuizMainActivity activity=(QuizMainActivity)getActivity();
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        add=view.findViewById(R.id.add);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        List<Question> list=activity.getSql().getQuestionByQuiz(activity.getQuiz());
        adapter.setContext(activity);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        if (activity.getQuiz().getUserId()!=activity.getUser().getId()){
            add.setVisibility(View.GONE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, AddQuestionActivity.class);
                intent.putExtra("quiz",activity.getQuiz());
                intent.putExtra("user",activity.getUser());
                intent.putExtra("method","POST");
                startActivity(intent);
            }
        });
    }
}