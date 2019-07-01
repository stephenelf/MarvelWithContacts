package com.stephenelf.simpleinterviewtestapp;

import android.app.Activity;
import android.app.Application;

import com.stephenelf.simpleinterviewtestapp.dagger.AppModule;
import com.stephenelf.simpleinterviewtestapp.dagger.DaggerNetComponent;
import com.stephenelf.simpleinterviewtestapp.dagger.DatabaseModule;
import com.stephenelf.simpleinterviewtestapp.dagger.NetComponent;
import com.stephenelf.simpleinterviewtestapp.dagger.NetModule;
import com.stephenelf.simpleinterviewtestapp.dagger.RepositoryModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {

    private NetComponent netComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule(getString(R.string.marver_base_url)))
                .databaseModule(new DatabaseModule())
                .repositoryModule(new RepositoryModule())
                .build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}