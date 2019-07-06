package com.example.modelviewwhateverpractice;

import android.app.Application;

import com.example.modelviewwhateverpractice.common.buildlogic.AppComponent;
import com.example.modelviewwhateverpractice.common.buildlogic.DaggerAppComponent;

import timber.log.Timber;

public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        initTimber();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }


    public AppComponent appComponent() {
        return appComponent;
    }
}
