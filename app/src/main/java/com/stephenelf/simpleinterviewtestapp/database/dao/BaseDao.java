package com.stephenelf.simpleinterviewtestapp.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<T> t);

    @Update
    void update(T t);

    @Delete
    void delete(T t);
}