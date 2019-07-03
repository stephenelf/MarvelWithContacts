package com.stephenelf.simpleinterviewtestapp.dagger;

import android.app.Application;

import com.stephenelf.simpleinterviewtestapp.Repository;
import com.stephenelf.simpleinterviewtestapp.database.MyDatabase;
import com.stephenelf.simpleinterviewtestapp.net.MarvelAPI;
import com.stephenelf.simpleinterviewtestapp.repositories.ContactsRepository;
import com.stephenelf.simpleinterviewtestapp.repositories.LocalRepository;
import com.stephenelf.simpleinterviewtestapp.repositories.RemoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public Repository providesRepository(Application application, LocalRepository localRepository, RemoteRepository remoteRepository, ContactsRepository contactsRepository){
        return new Repository(application,localRepository,remoteRepository,contactsRepository);
    }

    @Provides
    @Singleton
    public ContactsRepository providesContactRepository(){
        return new ContactsRepository();
    }

    @Provides
    @Singleton
    public LocalRepository providesLocalRepository(Application application, MyDatabase database){
        return new LocalRepository(application,database);
    }

    @Provides
    @Singleton
    public RemoteRepository providesRemoteRepository(Application application, MarvelAPI marvelAPI){
        return new RemoteRepository(application,marvelAPI);
    }
}
