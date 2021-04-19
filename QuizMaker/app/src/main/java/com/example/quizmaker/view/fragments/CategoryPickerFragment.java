package com.example.quizmaker.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizmaker.R;
import com.example.quizmaker.view.adapters.CategoryRecyclerViewAdapter;
import com.example.quizmaker.viewmodel.WordViewModel;

import java.util.List;

public class CategoryPickerFragment extends Fragment implements CategoryRecyclerViewAdapter.OnCategoryClickListener {

    private WordViewModel categoryPickerViewModel;
    private FragmentTransaction fragmentTransaction;
    private CategoryRecyclerViewAdapter adapter;

    private List<String> mCategoryList;


    public CategoryPickerFragment() {
        // Required empty public constructor
    }


    public static CategoryPickerFragment newInstance() {
        CategoryPickerFragment fragment = new CategoryPickerFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.category_recycler_view);

        categoryPickerViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(WordViewModel.class);

        categoryPickerViewModel.getCategoryList().observe(getViewLifecycleOwner(), strings -> {
            mCategoryList = strings;
            //Log.d("LIVEDATA_TEST_TAG", "I FILLED THE DATA SO...");

            adapter = new CategoryRecyclerViewAdapter(getContext(), mCategoryList, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }

    @Override
    public void onCategoryClick(int position) {
        Fragment QuizFragment = new QuizFragment(adapter.getCategoryAt(position));
        fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.quiz_fragment_container, QuizFragment);
        fragmentTransaction.commit();
    }
}