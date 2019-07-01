package com.stephenelf.simpleinterviewtestapp.dagger;

import android.app.Application;

import com.stephenelf.simpleinterviewtestapp.Repository;
import com.stephenelf.simpleinterviewtestapp.database.MyDatabase;

import dagger.Module;

@Module
public class RepositoryModule {

    public Repository providesRepository(Application application, MyDatabase myDatabase){
        return new Repository(application,myDatabase);
    }
}
