package com.example.modelviewwhateverpractice.itemlist;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.datamodel.Item;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class ItemListViewModelTest {

    private Application application = ApplicationProvider.getApplicationContext();

    private ItemListViewModel viewModel = new ItemListViewModel(application);

    /**
     * The view data passed-in should equal the view data retrieved.
     */
    @Test
    public void setAndGetViewData() {
        List<Item> itemList = new ArrayList<>();
        Item zero = new Item(0, "some title zero");
        itemList.add(zero);
        Item one = new Item(1, "some title one");
        itemList.add(one);

        viewModel.setViewData(itemList);

        // NOTE: the values in the contains method must be in the
        // exact same order as they are in the List.
        assertThat(viewModel.getViewData(), is(contains(zero, one)));
    }

    /**
     * Verify the method is retrieving the correct String.
     */
    @Test
    public void getMsgEmptyList() {
        assertThat(
                viewModel.getMsgEmptyList(),
                is(application.getString(R.string.error_msg_empty_list))
        );
    }

    /**
     * Verify the method is retrieving the correct String.
     */
    @Test
    public void getMsgError() {
        assertThat(
                viewModel.getMsgError(),
                is(application.getString(R.string.error_msg_generic))
        );
    }
}