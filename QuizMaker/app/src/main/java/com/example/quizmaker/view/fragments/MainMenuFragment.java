package com.example.quizmaker.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizmaker.R;
import com.example.quizmaker.view.QuizActivity;

import java.util.Objects;


public class MainMenuFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button mStartQuiz = view.findViewById(R.id.start_button);
        Button mManageQuestions = view.findViewById(R.id.question_set_manager_button);
        Button mExitButton = view.findViewById(R.id.exit_program_button);

        mStartQuiz.setOnClickListener(v -> {
            goToQuizActivity();
        });

        mManageQuestions.setOnClickListener(v -> {
            goToQuestionPicker();
        });

        mExitButton.setOnClickListener(v -> {
            exitApp();
        });
    }

    public void goToQuizActivity() {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        startActivity(intent);
    }

    public void goToQuestionPicker() {
        Fragment CategoryPickerFragment = new QuestionPickerFragment();
        fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, CategoryPickerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Toast.makeText(getContext(), "SWIPE ELEMENT TO DELETE", Toast.LENGTH_SHORT).show();
    }

    public void exitApp() {
        requireActivity().finish();
        System.exit(0);
    }
}