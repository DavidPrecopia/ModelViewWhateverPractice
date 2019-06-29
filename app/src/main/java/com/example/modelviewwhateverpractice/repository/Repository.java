package com.example.modelviewwhateverpractice.repository;

import android.app.Application;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDao;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public final class Repository implements IRepositoryContract.Repository {

    private ItemDao dao;

    private static IRepositoryContract.Repository repository;

    /**
     * Util I implement DI.
     */
    public static IRepositoryContract.Repository getInstance(Application application) {
        if (repository == null) {
            repository = new Repository(application);
        }
        return repository;
    }

    private Repository(Application application) {
        dao = ItemDatabase.getInstance(application).itemDao();
    }


    @Override
    public Flowable<List<Item>> observe() {
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
