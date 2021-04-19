package com.example.quizmaker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quizmaker.model.WordDAO;
import com.example.quizmaker.model.WordEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {

    private static volatile WordDatabase INSTANCE;

    private static final int NUM_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_OF_THREADS);

    public abstract WordDAO wordDao();

    public static synchronized WordDatabase getInstance(Context context) {
        //Log.d("TEST_INSTANCE", "INITIALIZE");
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class,
                    "word_database_test2")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return INSTANCE;
    }

    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                //Log.d("TEST_FILL", "FILLING DATABASE");
                WordDAO dao = INSTANCE.wordDao();
                dao.insert(new WordEntity("りんご", "apple", "fruits and vegetables"));
                dao.insert(new WordEntity("オレンジ", "orange", "fruits and vegetables"));
                dao.insert(new WordEntity("にんじん", "carrot", "fruits and vegetables"));
                dao.insert(new WordEntity("ピーマン", "pepper", "fruits and vegetables"));
                dao.insert(new WordEntity("マンゴー", "mango", "fruits and vegetables"));
                dao.insert(new WordEntity("でんしゃ", "train", "vehicle"));
                dao.insert(new WordEntity("りょかくき", "plane", "vehicle"));
                dao.insert(new WordEntity("じてんしゃ", "bicycle", "vehicle"));
            });
        }
    };

}
