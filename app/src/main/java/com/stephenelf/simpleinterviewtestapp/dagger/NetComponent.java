package com.stephenelf.simpleinterviewtestapp.dagger;

import com.stephenelf.simpleinterviewtestapp.MainActivity;
import com.stephenelf.simpleinterviewtestapp.MyApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, DatabaseModule.class, RepositoryModule.class})
public interface NetComponent {

    void inject(MyApplication myApplication);

    void inject(MainActivity mainActivity);

}
