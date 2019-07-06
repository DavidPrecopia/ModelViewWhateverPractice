package com.example.modelviewwhateverpractice.repository.buildlogic;

import android.app.Application;

import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.repository.Repository;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDao;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    IRepositoryContract.Repository repository(ItemDao dao) {
        return new Repository(dao);
    }

    @Singleton
    @Provides
    ItemDao itemDao(Application application) {
        return ItemDatabase.getInstance(application).itemDao();
    }
}
