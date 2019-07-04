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

package com.stephenelf.marvelwithcontacts.dagger;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.stephenelf.marvelwithcontacts.database.MyDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
            MyDatabase myDatabase= null;
            try {
                myDatabase = Room.databaseBuilder(application,
                        MyDatabase.class, "my_database.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }catch (Exception e){
                Log.e("KK","",e);
            }
            return myDatabase;
    }
}
