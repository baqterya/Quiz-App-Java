package com.example.quizmaker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;
    private String translation;
    private String category;

    public WordEntity(String word, String translation, String category) {
        this.word = word;
        this.translation = translation;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getCategory() {
        return category;
    }
}
