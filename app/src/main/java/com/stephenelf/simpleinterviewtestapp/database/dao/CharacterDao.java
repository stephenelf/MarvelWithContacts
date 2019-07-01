package com.stephenelf.simpleinterviewtestapp.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.stephenelf.simpleinterviewtestapp.database.Character;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface CharacterDao  extends BaseDao<Character> {

    @Query("SELECT * FROM Character")
    Maybe<List<Character>> getAllCharacters();
}
