package com.example.modelviewwhateverpractice.itemdetail.buildlogic;

import com.example.modelviewwhateverpractice.common.buildlogic.ViewScope;
import com.example.modelviewwhateverpractice.itemdetail.IDetailViewContract;
import com.example.modelviewwhateverpractice.itemdetail.ItemDetailLogic;
import com.example.modelviewwhateverpractice.itemdetail.ItemDetailViewModel;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.util.ISchedulerProviderContract;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
class ItemDetailModule {
    @ViewScope
    @Provides
    IDetailViewContract.Logic logic(IDetailViewContract.View view,
                                    IDetailViewContract.ViewModel viewModel,
                                    IRepositoryContract.Repository repository,
                                    ISchedulerProviderContract schedulerProvider,
                                    CompositeDisposable disposable) {
        return new ItemDetailLogic(view, viewModel, repository, schedulerProvider, disposable);
    }

    @ViewScope
    @Provides
    IDetailViewContract.ViewModel viewModel() {
        return new ItemDetailViewModel();
    }
}
