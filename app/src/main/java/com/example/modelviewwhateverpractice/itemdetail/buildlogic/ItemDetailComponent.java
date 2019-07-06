package com.example.modelviewwhateverpractice.itemdetail.buildlogic;

import android.app.Application;

import com.example.modelviewwhateverpractice.common.buildlogic.CommonViewModule;
import com.example.modelviewwhateverpractice.common.buildlogic.ViewScope;
import com.example.modelviewwhateverpractice.itemdetail.IDetailViewContract;
import com.example.modelviewwhateverpractice.itemdetail.ItemDetailView;

import dagger.BindsInstance;
import dagger.Component;

@ViewScope
@Component(modules = {
        ItemDetailModule.class,
        CommonViewModule.class
})
public interface ItemDetailComponent {
    void inject(ItemDetailView itemDetailView);

    @Component.Builder
    interface Builder {
        ItemDetailComponent build();

        @BindsInstance
        Builder view(IDetailViewContract.View view);

        @BindsInstance
        Builder application(Application application);
    }
}
