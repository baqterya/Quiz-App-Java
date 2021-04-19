package com.example.quizmaker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quizmaker.model.WordEntity;
import com.example.quizmaker.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository repository;
    private LiveData<List<WordEntity>> allWords;
    private LiveData<List<WordEntity>> wordsByCategory;
    private LiveData<List<String>> categoryList;

    public WordViewModel(@NonNull Application application) {
        super(application);

        repository = new WordRepository(application);
        allWords = repository.getAllWords();
    }

    public void insert(WordEntity word) {
       repository.insert(word);
    }

    public void update(WordEntity word) {
        repository.update(word);
    }

    public void delete(WordEntity word) {
        repository.delete(word);
    }

    public LiveData<List<String>> getCategoryList() {
        categoryList = repository.getCategoryList();
        return categoryList;
    }

    public LiveData<List<WordEntity>> getWordsByCategory(String category) {
        wordsByCategory = repository.getWordsByCategory(category);
        return wordsByCategory;
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return allWords;
    }

    public void deleteWordsByCategory(String category) {
        repository.deleteWordsByCategory(category);
    }

    public void deleteAllWords() {
        repository.deleteAllWords();
    }
}
