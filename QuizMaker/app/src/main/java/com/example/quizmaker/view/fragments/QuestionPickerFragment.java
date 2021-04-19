package com.example.quizmaker.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizmaker.R;
import com.example.quizmaker.model.WordEntity;
import com.example.quizmaker.view.adapters.QuestionRecyclerViewAdapter;
import com.example.quizmaker.viewmodel.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class QuestionPickerFragment extends Fragment implements QuestionRecyclerViewAdapter.OnWordClickListener{

    private WordViewModel questionPickerViewModel;
    private FragmentTransaction fragmentTransaction;
    private WordEntity mWordEntity;
    private QuestionRecyclerViewAdapter adapter;

    public QuestionPickerFragment() {
        // Required empty public constructor
    }

    public QuestionPickerFragment(WordEntity wordEntity) {

        mWordEntity = wordEntity;
    }

    public static QuestionPickerFragment newInstance() {
        QuestionPickerFragment fragment = new QuestionPickerFragment();
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
        return inflater.inflate(R.layout.fragment_question_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.question_recycler_view);
        adapter = new QuestionRecyclerViewAdapter(this);

        FloatingActionButton fab = view.findViewById(R.id.add_question_action_button);
        fab.setOnClickListener(v -> {
            Fragment QuestionAddEditFragment = new QuestionAddEditFragment();
            fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, QuestionAddEditFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        questionPickerViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(WordViewModel.class);

        questionPickerViewModel.getAllWords().observe(getActivity(), adapter::submitList);

        if (mWordEntity != null) {
            getParentFragmentManager().popBackStack();
            questionPickerViewModel.insert(mWordEntity);
        }

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                questionPickerViewModel.delete(adapter.getWordEntityAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "WORD DELETED", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onWordClick(int position) {
        // TODO
    }
}

