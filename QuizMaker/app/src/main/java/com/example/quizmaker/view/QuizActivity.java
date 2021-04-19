package com.example.quizmaker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.quizmaker.R;
import com.example.quizmaker.view.fragments.CategoryPickerFragment;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.quiz_fragment_container, new CategoryPickerFragment());
        fragmentTransaction.commit();
    }
}


// THIS WORKS!!!!
/*
        viewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication())).get(WordViewModel.class);

        viewModel.getWordsByCategory("fruits and vegetables").observe(this, wordEntities -> {
            test = wordEntities;
            test.forEach(wordEntity -> {
                Log.d("LIVEDATA_TEST_TAG", wordEntity.getWord().toString());
            });
        });

        viewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication())).get(WordViewModel.class);

        viewModel.getCategoryList().observe(this, strings -> {
            test = strings;
            test.forEach(category -> {
                Log.d("LIVEDATA_TEST_TAG", category);
            });
        });
*/