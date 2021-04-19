package com.example.quizmaker.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDAO {
    @Insert
    void insert(WordEntity word);

    @Update
    void update (WordEntity word);

    @Delete
    void delete(WordEntity word);

    @Query("DELETE FROM word_table")
    void deleteAllWords();

    @Query("SELECT * FROM word_table WHERE category =:category ORDER BY translation ASC")
    LiveData<List<WordEntity>> getWordsByCategory(String category);

    @Query("SELECT DISTINCT category FROM word_table")
    LiveData<List<String>> getAllCategories();

    @Query("DELETE FROM word_table WHERE category =:category")
    void deleteWordsByCategory(String category);

    @Query("SELECT * FROM word_table ORDER BY category ASC, translation ASC")
    LiveData<List<WordEntity>> getAllWords();
}
