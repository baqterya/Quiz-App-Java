package com.example.quizmaker.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizmaker.R;
import com.example.quizmaker.model.WordEntity;
import com.example.quizmaker.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuizFragment extends Fragment {

    private WordViewModel quizViewModel;
    private String mCategory;
    private FragmentTransaction fragmentTransaction;

    private ArrayList<String> mWords = new ArrayList<>();
    private ArrayList<String> mTranslations = new ArrayList<>();
    private int mPoints = 0;
    private int mCurrentQuestionId;
    private int mQuestionsAsked = 0;

    private TextView questionTextView;
    private Button optionOne;
    private Button optionTwo;
    private Button optionThree;
    private Button optionFour;


    public QuizFragment() {
        // Required empty public constructor
    }

    public QuizFragment(String categoryName) {
        mCategory = categoryName;
    }


    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionTextView = view.findViewById(R.id.quiz_question_text_view);
        optionOne = view.findViewById(R.id.quiz_button_one);
        optionTwo = view.findViewById(R.id.quiz_button_two);
        optionThree = view.findViewById(R.id.quiz_button_three);
        optionFour = view.findViewById(R.id.quiz_button_four);

        quizViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(WordViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();

        quizViewModel.getWordsByCategory(mCategory).observe(getViewLifecycleOwner(), new Observer<List<WordEntity>>() {
            @Override
            public void onChanged(List<WordEntity> wordEntities) {
                if (wordEntities.size() < 4) {
                    questionTextView.setText("THIS QUESTION SET IS TOO SMALL.\n IT NEEDS AT LEAST 4 WORDS");
                    requireView().findViewById(R.id.quiz_text_view_1).setVisibility(View.INVISIBLE);
                    optionOne.setVisibility(View.INVISIBLE);
                    optionTwo.setVisibility(View.INVISIBLE);
                    optionThree.setVisibility(View.INVISIBLE);
                    optionFour.setVisibility(View.INVISIBLE);
                }
                else {
                    setupQuiz(wordEntities);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        quizViewModel.getWordsByCategory(mCategory).removeObservers(getViewLifecycleOwner());
    }

    void setupQuiz(List<WordEntity> wordEntities) {
        wordEntities.forEach(word -> {
            mWords.add(word.getWord());
            mTranslations.add(word.getTranslation());
        });

        optionOne.setOnClickListener(v -> {
            checkAnswer(optionOne.getText().toString());
        });
        optionTwo.setOnClickListener(v -> {
            checkAnswer(optionTwo.getText().toString());
        });
        optionThree.setOnClickListener(v -> {
            checkAnswer(optionThree.getText().toString());
        });
        optionFour.setOnClickListener(v -> {
            checkAnswer(optionFour.getText().toString());
        });

        updateQuestion();
    }

    void checkAnswer(String answer) {
        if (answer.equals(mTranslations.get(mCurrentQuestionId)))
            mPoints++;

        mQuestionsAsked++;

        if (mQuestionsAsked == 5) {
            Fragment ScoreFragment = new ScoreFragment(mPoints);
            fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.quiz_fragment_container, ScoreFragment);
            fragmentTransaction.commit();
        }
        else
            updateQuestion();
    }

    void updateQuestion() {
        mCurrentQuestionId = ThreadLocalRandom.current().nextInt(0, mWords.size());
        questionTextView.setText(mWords.get(mCurrentQuestionId));

        ArrayList<Button> randomButton = new ArrayList<>();
        randomButton.add(optionOne);
        randomButton.add(optionTwo);
        randomButton.add(optionThree);
        randomButton.add(optionFour);

        ArrayList<Integer> usedId = new ArrayList<>();

        ArrayList<String> usedAnswers = new ArrayList<>();
        Random rand = new Random();

        while (usedAnswers.size() < 4) {
            int randomButtonIndex = rand.nextInt(randomButton.size());
            if (!usedId.contains(randomButtonIndex)) {

                if (usedAnswers.size() == 0) {
                    randomButton.get(randomButtonIndex).setText(mTranslations.get(mCurrentQuestionId));
                    usedAnswers.add(mTranslations.get(mCurrentQuestionId));
                }
                else {
                    int randomAnswerIndex = rand.nextInt(mTranslations.size());
                    //problem z tym if co ma być tu
                    randomButton.get(randomButtonIndex).setText(mTranslations.get(randomAnswerIndex));
                    usedAnswers.add(mTranslations.get(randomAnswerIndex));
                }

                usedId.add(randomButtonIndex);
            }
        }
    }

}