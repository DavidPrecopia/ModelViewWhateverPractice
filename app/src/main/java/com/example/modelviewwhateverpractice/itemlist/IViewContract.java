package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.List;

import io.reactivex.Flowable;

interface IViewContract {
    interface View {
        void setList(List<Item> itemList);

        void uiStateLoading();

        void uiStateDisplayList();

        void uiStateError(String message);
    }

    interface Logic {
        void onItemClicked(int position);

        void onStart();

        void onPause();
    }

    /**
     * Thanks to JetPack's ViewModel this will persist util the View is finished.
     * Because of that, this will directly talk to the Repository.
     */
    interface ViewModel {
        Flowable<List<Item>> subscribe();
    }
}
