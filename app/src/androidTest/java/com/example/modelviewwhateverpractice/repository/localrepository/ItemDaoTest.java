package com.example.modelviewwhateverpractice.repository.localrepository;

import android.app.Application;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.modelviewwhateverpractice.datamodel.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    private ItemDao dao;
    private ItemDatabase database;

    private Item testingItem;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(application, ItemDatabase.class).build();
        dao = database.itemDao();

        testingItem = new Item("title");
    }

    @After
    public void tearDown() {
        database.close();
    }


    /**
     * Testing normal behavior - Items added should be within the
     * Flowable returned by {@link ItemDao#getAllItems()}.
     * <p>
     * 1. Add an Item to the database.
     * 2. Verify that the added Item is in the Flowable returned by get all.
     */
    @Test
    public void testGettingAllItems() {
        Long id = dao.addItem(testingItem);

        assertThatInsertIsValid(id);

        dao.getAllItems()
                .test()
                .awaitDone(3, TimeUnit.SECONDS)
                .assertValue(items ->
                        items.contains(new Item(id.intValue(), testingItem.getTitle()))
                )
                .dispose();
    }


    /**
     * If nothing has been added to the database, the Flowable should be empty.
     *
     * 1. Get all items and verify the returned Flowable is empty.
     */
    @Test
    public void testGettingAllItemsFromEmptyDatabase() {
        dao.getAllItems()
                .test()
                .assertEmpty()
                .dispose();
    }


    /**
     * 1. Add an Item to the database.
     * 2. Retrieve the same Item with the DAO's getter method.
     */
    @Test
    public void testAddingThenGettingAnItem() {
        Long id = dao.addItem(testingItem);

        assertThatInsertIsValid(id);

        dao.getItem(id.intValue())
                .test()
                .assertResult(new Item(id.intValue(), testingItem.getTitle()))
                .dispose();
    }


    private void assertThatInsertIsValid(Long id) {
        assertThat(id, is(greaterThanOrEqualTo(0L)));
    }
}