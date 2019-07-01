package com.stephenelf.simpleinterviewtestapp.dagger;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.stephenelf.simpleinterviewtestapp.database.MyDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application, SharedPreferences sharedPreferences) {
            return Room.databaseBuilder(application,
                    MyDatabase.class, "my_database.db")
                        .fallbackToDestructiveMigration()
                    .build();
    }
}
