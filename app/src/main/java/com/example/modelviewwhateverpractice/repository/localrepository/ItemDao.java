package com.example.modelviewwhateverpractice.repository.localrepository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM " + ItemDbConstants.ITEM_TABLE)
    Flowable<List<Item>> getAllItems();

    @Query("SELECT * FROM " + ItemDbConstants.ITEM_TABLE + " WHERE :id = " + ItemDbConstants.COLUMN_ID)
    Single<Item> getItem(int id);

    @Insert
    long addItem(Item item);
}
