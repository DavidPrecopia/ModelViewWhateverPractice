package com.example.modelviewwhateverpractice.repository;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IRepositoryContract {
    /**
     * Using Flowable, not Observable, in my projects in case at
     * some point in the future back-pressure becomes needed.
     */
    interface Repository {
        Flowable<List<Item>> observe();

        Single<Item> getItem(int itemId);

        Completable addItem(Item item);
    }
}
