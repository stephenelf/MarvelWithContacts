package com.stephenelf.simpleinterviewtestapp.dagger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, DatabaseModule.class, RepositoryModule.class})
public interface NetComponent {


}
