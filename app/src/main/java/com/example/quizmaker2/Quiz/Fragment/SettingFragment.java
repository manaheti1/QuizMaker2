package com.example.quizmaker2.Quiz.Fragment;

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
import android.widget.Switch;
import android.widget.Toast;

import com.example.quizmaker2.Main.Main;
import com.example.quizmaker2.Quiz.QuizMainActivity;
import com.example.quizmaker2.R;
import com.example.quizmaker2.Train.TrainActivity;
import com.example.quizmaker2.database.models.Quiz;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    Button Confirm,Delete;
    Switch state;
    EditText name,questpertest,timeperq,des;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QuizMainActivity activity=(QuizMainActivity)getActivity();
        Confirm=activity.findViewById(R.id.confirm);
        Delete=activity.findViewById(R.id.delete);
        state=activity.findViewById(R.id.state);
        name=activity.findViewById(R.id.Qname);
        questpertest=activity.findViewById(R.id.Qcount);
        timeperq=activity.findViewById(R.id.Qtime);
        des=activity.findViewById(R.id.QDes);
        name.setText(activity.getQuiz().getName());
        if (activity.getQuiz().getState().equals("Private"))
            state.setChecked(false);
        else state.setChecked(true);
        des.setText(activity.getQuiz().getDescription());
        timeperq.setText(Integer.toString(activity.getQuiz().getTime()));
        questpertest.setText(Integer.toString(activity.getQuiz().getQuestionpertest()));
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quiz quiz=new Quiz(activity.getQuiz().getId(),name.getText().toString(),activity.getUser().getId(),des.getText().toString(),Integer.parseInt(timeperq.getText().toString()),Integer.parseInt(questpertest.getText().toString()),state.isChecked()?"Public":"Private");
                activity.getSql().editQuiz(quiz);
                Toast.makeText(activity,"Updated",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, Main.class);
                intent.putExtra("user",activity.getUser());
                startActivity(intent);
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSql().deleteQuiz(activity.getQuiz());
                Intent intent=new Intent(activity, Main.class);
                intent.putExtra("user",activity.getUser());
                activity.finish();
                startActivity(
                        intent
                );
            }
        });
        if (activity.getUser().getId()!=activity.getQuiz().getUserId()){
            name.setFocusable(false);
            timeperq.setFocusable(false);
            des.setFocusable(false);
            questpertest.setFocusable(false);
            Confirm.setEnabled(false);
            Delete.setEnabled(false);
            state.setClickable(false);
        }
    }
}