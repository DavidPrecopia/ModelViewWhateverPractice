package com.example.modelviewwhateverpractice.common.buildlogic;

import android.app.Application;

import androidx.room.Room;

import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.repository.Repository;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDao;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDatabase;
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
        ItemDao itemDao = Room.inMemoryDatabaseBuilder(application, ItemDatabase.class)
                .build()
                .itemDao();
        return new Repository(itemDao);
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
