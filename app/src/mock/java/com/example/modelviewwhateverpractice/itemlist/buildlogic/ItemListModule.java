package com.example.modelviewwhateverpractice.itemlist.buildlogic;

import com.example.modelviewwhateverpractice.common.buildlogic.ViewScope;
import com.example.modelviewwhateverpractice.itemlist.IListViewContract;

import dagger.Module;
import dagger.Provides;

@Module
class ItemListModule {
    @ViewScope
    @Provides
    IListViewContract.Logic logic() {
        return new ListViewLogicMock();
    }
}
