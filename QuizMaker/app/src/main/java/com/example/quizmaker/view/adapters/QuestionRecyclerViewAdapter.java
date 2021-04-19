package com.example.quizmaker.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker.R;
import com.example.quizmaker.model.WordEntity;

public class QuestionRecyclerViewAdapter extends ListAdapter<WordEntity, QuestionRecyclerViewAdapter.QuestionHolder> {

    private OnWordClickListener onWordClickListener;

    public QuestionRecyclerViewAdapter(OnWordClickListener onWordClickListener) {
        super(DIFF_CALLBACK);
        this.onWordClickListener = onWordClickListener;
    }

    private static final DiffUtil.ItemCallback<WordEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<WordEntity>() {
        @Override
        public boolean areItemsTheSame(WordEntity oldItem, WordEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(WordEntity oldItem, WordEntity newItem) {
            return oldItem.getWord().equals(newItem.getWord()) &&
                    oldItem.getTranslation().equals(newItem.getTranslation()) &&
                    oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.getId() == newItem.getId();
        }
    };
    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item_view, parent, false);
        return new QuestionHolder(itemView, onWordClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        WordEntity currentWord = getItem(position);
        holder.wordTextView.setText(currentWord.getWord());
        holder.translationTextView.setText(currentWord.getTranslation());
        holder.categoryTextView.setText(currentWord.getCategory());
    }

    static class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView wordTextView;
        private TextView translationTextView;
        private TextView categoryTextView;
        OnWordClickListener onWordClickListener;

        public QuestionHolder(View itemView, OnWordClickListener onWordClickListener) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.word_text_view);
            translationTextView = itemView.findViewById(R.id.translation_text_view);
            categoryTextView = itemView.findViewById(R.id.category_name_text_view);
            this.onWordClickListener = onWordClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onWordClickListener.onWordClick(getAdapterPosition());
        }
    }

    public WordEntity getWordEntityAt(int position) {
        return getItem(position);
    }

    public interface OnWordClickListener {
        void onWordClick(int position);
    }

}