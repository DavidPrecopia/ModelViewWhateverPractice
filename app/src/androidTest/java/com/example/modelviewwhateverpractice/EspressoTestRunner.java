package com.example.modelviewwhateverpractice;

import androidx.test.runner.AndroidJUnitRunner;

import com.squareup.rx2.idler.Rx2Idler;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * To be used with the prod flavor for an integrated Espresso test.
 */
public class EspressoTestRunner extends AndroidJUnitRunner {
    @Override
    public void onStart() {
        initRxIdle();
        super.onStart();
    }

    private void initRxIdle() {
        RxJavaPlugins.setInitIoSchedulerHandler(
                Rx2Idler.create("RxJava IO Scheduler")
        );

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                Rx2Idler.create("RxAndroid Main Thread Scheduler")
        );
    }
}
