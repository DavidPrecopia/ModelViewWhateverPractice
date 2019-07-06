package com.example.modelviewwhateverpractice.itemlist.buildlogic;

import android.app.Application;

import com.example.modelviewwhateverpractice.common.buildlogic.CommonViewModule;
import com.example.modelviewwhateverpractice.common.buildlogic.ViewScope;
import com.example.modelviewwhateverpractice.itemlist.IListViewContract;
import com.example.modelviewwhateverpractice.itemlist.ItemListView;

import dagger.BindsInstance;
import dagger.Component;

@ViewScope
@Component(modules = {
        ItemListModule.class,
        CommonViewModule.class
})
public interface ItemListComponent {
    void inject(ItemListView itemListView);

    @Component.Builder
    interface Builder {
        ItemListComponent build();

        @BindsInstance
        Builder view(IListViewContract.View view);

        @BindsInstance
        Builder application(Application application);
    }
}
