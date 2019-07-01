package com.stephenelf.simpleinterviewtestapp.repositories;

import android.content.Context;

import com.stephenelf.simpleinterviewtestapp.database.Character;
import com.stephenelf.simpleinterviewtestapp.database.MyDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocalRepository {

    private Context Conxext;

    private MyDatabase myDatabase;

    public LocalRepository(Context conxext, MyDatabase myDatabase) {
        Conxext = conxext;
        this.myDatabase = myDatabase;
    }

    public Maybe<List<Character>> getAllCharacters(){
        return myDatabase.characterDao.getAllCharacters();
    }

    public void saveCharacters(final List<Character> characters){
        Completable.fromAction(()-> myDatabase.characterDao.insert(characters))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
