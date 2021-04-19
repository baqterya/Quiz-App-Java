package com.example.quizmaker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.quizmaker.database.WordDatabase;
import com.example.quizmaker.model.WordDAO;
import com.example.quizmaker.model.WordEntity;

import java.util.List;

public class WordRepository {

    private WordDAO wordDao;
    private LiveData<List<WordEntity>> allWords;
    private LiveData<List<WordEntity>> wordsByCategory;
    private LiveData<List<String>> categoryList;


    public WordRepository(Application application) {
        WordDatabase database = WordDatabase.getInstance(application);
        wordDao = database.wordDao();
        allWords = wordDao.getAllWords();
    }

    public void insert(WordEntity word) {
        WordDatabase.databaseWriteExecutor.execute(() -> {
            wordDao.insert(word);
        });
    }

    public void update(WordEntity word) {
        WordDatabase.databaseWriteExecutor.execute(() -> {
            wordDao.update(word);
        });
    }

    public void delete(WordEntity word) {
        WordDatabase.databaseWriteExecutor.execute(() -> {
            wordDao.delete(word);
        });
    }

    public LiveData<List<WordEntity>> getWordsByCategory(String category) {
        wordsByCategory = wordDao.getWordsByCategory(category);

        return wordsByCategory;
    }

    public LiveData<List<String>> getCategoryList() {
        categoryList = wordDao.getAllCategories();

        return categoryList;
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return allWords;
    }

    public void deleteWordsByCategory(String category) {
        WordDatabase.databaseWriteExecutor.execute(() -> {
            wordDao.deleteWordsByCategory(category);
        });
    }

    public void deleteAllWords() {
        WordDatabase.databaseWriteExecutor.execute(() -> {
            wordDao.deleteAllWords();
        });
    }

}
