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
    public LocalRepository providesLocalRepository( MyDatabase database){
        return new LocalRepository(database);
    }

    @Provides
    @Singleton
    public RemoteRepository providesRemoteRepository(Application application, MarvelAPI marvelAPI){
        return new RemoteRepository(application,marvelAPI);
    }
}
