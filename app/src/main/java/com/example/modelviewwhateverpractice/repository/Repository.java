package com.example.modelviewwhateverpractice.repository;

import android.app.Application;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDao;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

public final class Repository implements IRepositoryContract.Repository {

    private ItemDao dao;

    public Repository(Application application) {
        dao = ItemDatabase.getInstance(application).itemDao();
    }


    @Override
    public Flowable<List<Item>> observe() {
        Timber.i("REPOSITORY OBSERVE METHOD");
        return dao.getAllItems();
    }

    @Override
    public Single<Item> getItem(int itemId) {
        return dao.getItem(itemId);
    }

    @Override
    public Completable addItem(Item item) {
        return Completable.fromCallable(() -> (dao.addItem(item) >= 0));
    }

    @Override
    public Completable addItems(List<Item> items) {
        return Completable.fromCallable(() -> (dao.addItems(items).length >= 0));
    }
}
