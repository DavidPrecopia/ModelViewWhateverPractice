package com.example.modelviewwhateverpractice.common.buildlogic;

import android.app.Application;

import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.repository.buildlogic.RepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        RepositoryModule.class
})
public interface AppComponent {

    IRepositoryContract.Repository repository();

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
