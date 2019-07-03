/*
 * Copyright 2019 stephenelf@gmail.com(EB). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stephenelf.simpleinterviewtestapp.repositories;

import com.stephenelf.simpleinterviewtestapp.database.Character;
import com.stephenelf.simpleinterviewtestapp.database.MyDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocalRepository {

    private MyDatabase myDatabase;


    public LocalRepository(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }

    public Maybe<List<Character>> getAllCharacters() {
        return myDatabase.characterDao().getAllCharacters();
    }

    public void saveCharacters(final List<Character> characters) {
        Completable.fromAction(() -> myDatabase.characterDao().insert(characters))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }
}
