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

package com.stephenelf.simpleinterviewtestapp;

import android.app.Activity;
import android.app.Application;

import com.stephenelf.simpleinterviewtestapp.dagger.AppModule;
import com.stephenelf.simpleinterviewtestapp.dagger.DaggerMyCoolComponent;
import com.stephenelf.simpleinterviewtestapp.dagger.DatabaseModule;
import com.stephenelf.simpleinterviewtestapp.dagger.MyCoolComponent;
import com.stephenelf.simpleinterviewtestapp.dagger.NetModule;
import com.stephenelf.simpleinterviewtestapp.dagger.RepositoryModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {

    private MyCoolComponent netComponent;
    private static MyApplication INSTANCE;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE= this;
        netComponent = DaggerMyCoolComponent.builder()
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

    public static MyApplication getInstance(){
        return INSTANCE;
    }

    public MyCoolComponent getNetComponent() {
        return netComponent;
    }
}