package com.example.modelviewwhateverpractice.common.buildlogic;

import android.app.Application;

import com.example.modelviewwhateverpractice.MyApplication;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.util.ISchedulerProviderContract;
import com.example.modelviewwhateverpractice.util.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class CommonViewModule {
    @ViewScope
    @Provides
    IRepositoryContract.Repository repository(Application application) {
        return ((MyApplication) application).appComponent().repository();
    }

    @ViewScope
    @Provides
    ISchedulerProviderContract schedulerProvider() {
        return new SchedulerProvider();
    }

    @ViewScope
    @Provides
    CompositeDisposable disposable() {
        return new CompositeDisposable();
    }
}
