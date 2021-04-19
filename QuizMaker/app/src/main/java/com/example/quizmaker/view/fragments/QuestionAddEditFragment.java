package com.example.quizmaker.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizmaker.R;
import com.example.quizmaker.model.WordEntity;


public class QuestionAddEditFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;

    private EditText mWordEditText;
    private EditText mTranslationEditText;
    private EditText mCategoryEditText;
    private Button mAddButton;

    public QuestionAddEditFragment() {
        // Required empty public constructor
    }

    public static QuestionAddEditFragment newInstance() {
        QuestionAddEditFragment fragment = new QuestionAddEditFragment();
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

        return inflater.inflate(R.layout.fragment_question_add_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWordEditText = view.findViewById(R.id.add_word_edit_text);
        mTranslationEditText = view.findViewById(R.id.add_translation_edit_text);
        mCategoryEditText = view.findViewById(R.id.add_category_edit_text);
        mAddButton = view.findViewById(R.id.add_word_button);

        mAddButton.setOnClickListener(v -> {
            saveWord();
        });
    }

    private void saveWord() {
        String word = mWordEditText.getText().toString();
        String translation = mTranslationEditText.getText().toString();
        String category = mCategoryEditText.getText().toString();

        if (word.trim().isEmpty() || translation.trim().isEmpty() | category.trim().isEmpty()) {
            Toast.makeText(getContext(), "PLEASE FILL ALL FIELDS", Toast.LENGTH_SHORT).show();
            return;
        }

        Fragment QuestionPickerFragment;
        QuestionPickerFragment = new QuestionPickerFragment(new WordEntity(word, translation, category));
        fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, QuestionPickerFragment);
        fragmentTransaction.commit();
    }
}