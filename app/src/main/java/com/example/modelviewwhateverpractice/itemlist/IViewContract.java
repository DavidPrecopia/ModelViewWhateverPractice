package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.List;

interface IViewContract {
    interface View {
        void setList(List<Item> itemList);

        void uiStateLoading();

        void uiStateDisplayList();
    }

    interface Logic {
        void onStart();

        void onItemClicked(int position);
    }

    // TODO Get Logic working, then add this.
    interface ViewModel {
        void updateCache(List<Item> itemList);

        List<Item> getCache();
    }
}
