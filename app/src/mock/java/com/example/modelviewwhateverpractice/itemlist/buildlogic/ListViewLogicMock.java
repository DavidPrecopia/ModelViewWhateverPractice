package com.example.modelviewwhateverpractice.itemlist.buildlogic;

import androidx.recyclerview.widget.RecyclerView;

import com.example.modelviewwhateverpractice.itemlist.IListViewContract;

public class ListViewLogicMock implements IListViewContract.Logic {
    @Override
    public void onStart() {

    }

    @Override
    public void onItemClicked(String title) {

    }

    @Override
    public void addItem() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return null;
    }
}
