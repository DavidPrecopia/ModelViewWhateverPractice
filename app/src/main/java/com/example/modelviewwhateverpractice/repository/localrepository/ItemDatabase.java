package com.example.modelviewwhateverpractice.repository.localrepository;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.modelviewwhateverpractice.datamodel.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase database;

    public static ItemDatabase getInstance(Application application) {
        if (database == null) {
            database = Room.databaseBuilder(
                    application,
                    ItemDatabase.class,
                    ItemDbConstants.ITEM_DATABASE
            ).build();
        }
        return database;
    }

    public abstract ItemDao itemDao();
}
