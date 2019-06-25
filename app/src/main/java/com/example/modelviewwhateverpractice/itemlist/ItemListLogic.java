package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemListLogic implements IViewContract.Logic {

    private final List<Item> itemList;

    private final IViewContract.View view;

    ItemListLogic(IViewContract.View view) {
        this.view = view;
        this.itemList = new ArrayList<>();
    }


    @Override
    public void onStart() {
        init();
    }

    private void init() {
        view.uiStateLoading();
        getData();
        view.setList(itemList);
        view.uiStateDisplayList();
    }

    private void getData() {
        // until I have some sort of repository implemented.
        for (int x = 0; x < 20; x++) {
            this.itemList.add(new Item(String.valueOf(x)));
        }
    }

    @Override
    public void onItemClicked(int position) {
        throw new IllegalStateException("not implemented");
    }
}
