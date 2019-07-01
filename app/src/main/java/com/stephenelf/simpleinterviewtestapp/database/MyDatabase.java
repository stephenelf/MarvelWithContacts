package com.stephenelf.simpleinterviewtestapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.stephenelf.simpleinterviewtestapp.database.dao.CharacterDao;

@Database(entities = {Character.class},
        exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public CharacterDao characterDao;
}
