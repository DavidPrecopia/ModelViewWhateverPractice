package com.example.modelviewwhateverpractice.itemlist;

import android.app.Application;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.ArrayList;
import java.util.List;

public final class ItemListViewModel implements IListViewContract.ViewModel {

    private List<Item> itemList;

    private final Application application;

    public ItemListViewModel(Application application) {
        this.application = application;
        this.itemList = new ArrayList<>();
    }


    /**
     * If I clear the local field and then add all items from the passed-in List,
     * then the field will not be ready for the Logic class.
     * There is a better way to write this...
     */
    @Override
    public void setViewData(List<Item> items) {
        this.itemList = items;
    }

    @Override
    public List<Item> getViewData() {
        return itemList;
    }


    @Override
    public String getMsgEmptyList() {
        return getStringResource(R.string.error_msg_empty_list);
    }

    @Override
    public String getMsgError() {
        return getStringResource(R.string.error_msg_generic);
    }

    private String getStringResource(int resId) {
        return application.getString(resId);
    }
}
