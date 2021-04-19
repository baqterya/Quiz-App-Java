package com.example.quizmaker.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmaker.R;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {
    
    private final List<String> mCategoryList;
    private LayoutInflater mInflater;

    private OnCategoryClickListener onCategoryClickListener;
    
    public CategoryRecyclerViewAdapter(Context context, List<String> data, OnCategoryClickListener onCategoryClickListener) {
        mInflater = LayoutInflater.from(context);
        mCategoryList = data;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.category_item_view, parent, false);
        return new CategoryViewHolder(view, onCategoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String mCurrent = mCategoryList.get(position);
        holder.categoryTextView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView categoryTextView;
        OnCategoryClickListener onCategoryClickListener;

        public CategoryViewHolder(@NonNull View itemView, OnCategoryClickListener onCategoryClickListener) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_item_view);
            this.onCategoryClickListener = onCategoryClickListener;

            itemView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View v) {
            onCategoryClickListener.onCategoryClick(getAdapterPosition());
        }
    }

    public String getCategoryAt(int position) {
        return mCategoryList.get(position);
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }
}
