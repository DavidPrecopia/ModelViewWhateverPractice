package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.List;

interface IListViewContract {
    interface View {
        void setList(List<Item> itemList);

        void setUiStateLoading();

        void setUiStateDisplayList();

        void setUiStateError(String message);

        void openDetailView(String title);
    }

    interface Logic {
        void onItemClicked(String title);

        void onStart();

        void onDestroy();
    }

    interface ViewModel {
        void setViewData(List<Item> items);

        List<Item> getViewData();

        String getMsgEmptyList();

        String getMsgError();
    }
}