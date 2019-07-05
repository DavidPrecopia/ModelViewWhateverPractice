package com.example.modelviewwhateverpractice.itemlist;

import androidx.recyclerview.widget.RecyclerView;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.List;

interface IListViewContract {
    interface View {
        void setUiStateLoading();

        void setUiStateDisplayList();

        void setUiStateError(String message);

        void openDetailView(String title);
    }

    interface ViewAdapter {
        /**
         * Because the ViewAdapter is passed to the Logic class,
         * you cannot pass the Logic class to ViewAdapter's constructor.
         */
        void init(IListViewContract.Logic logic);

        void setViewData(List<Item> itemList);
    }

    interface Logic {
        void onStart();

        void onItemClicked(String title);

        void addItem();

        void onDestroy();

        RecyclerView.Adapter getAdapter();
    }

    interface ViewModel {
        void setViewData(List<Item> items);

        List<Item> getViewData();

        String getMsgEmptyList();

        String getMsgError();
    }
}
