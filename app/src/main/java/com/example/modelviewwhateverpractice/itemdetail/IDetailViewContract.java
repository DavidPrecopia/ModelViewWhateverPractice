package com.example.modelviewwhateverpractice.itemdetail;

interface IDetailViewContract {
    interface View {
        void setViewData(String title);
    }

    interface Logic {
        void onStart(String title);

        void onDestroy();

        void addItem();
    }

    interface ViewModel {
        void setViewData(String title);

        String getViewData();
    }
}
