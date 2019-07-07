package com.example.modelviewwhateverpractice.itemlist.buildlogic;

import android.app.Application;

import com.example.modelviewwhateverpractice.common.buildlogic.ViewScope;
import com.example.modelviewwhateverpractice.itemlist.IListViewContract;
import com.example.modelviewwhateverpractice.itemlist.ItemAdapter;
import com.example.modelviewwhateverpractice.itemlist.ItemListLogic;
import com.example.modelviewwhateverpractice.itemlist.ItemListViewModel;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.util.ISchedulerProviderContract;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
class ItemListModule {
    @ViewScope
    @Provides
    IListViewContract.Logic logic(IListViewContract.View view,
                                  IListViewContract.ViewAdapter adapter,
                                  IListViewContract.ViewModel viewModel,
                                  IRepositoryContract.Repository repository,
                                  ISchedulerProviderContract schedulerProvider,
                                  CompositeDisposable disposable) {
        return new ItemListLogic(view, adapter, viewModel, repository, schedulerProvider, disposable);
    }

    @ViewScope
    @Provides
    IListViewContract.ViewAdapter adapter() {
        return new ItemAdapter();
    }

    @ViewScope
    @Provides
    IListViewContract.ViewModel viewModel(Application application) {
        return new ItemListViewModel(application);
    }
}
