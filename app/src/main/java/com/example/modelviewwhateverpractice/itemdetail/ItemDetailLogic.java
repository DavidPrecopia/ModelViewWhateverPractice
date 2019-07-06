package com.example.modelviewwhateverpractice.itemdetail;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.util.ISchedulerProviderContract;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import timber.log.Timber;

public class ItemDetailLogic implements IDetailViewContract.Logic {

    static final String title = "ADDED FROM DETAIL";

    private final IDetailViewContract.View view;
    private final IDetailViewContract.ViewModel viewModel;

    private final IRepositoryContract.Repository repository;
    private final ISchedulerProviderContract schedulerProvider;
    private final CompositeDisposable disposable;

    public ItemDetailLogic(IDetailViewContract.View view,
                           IDetailViewContract.ViewModel viewModel,
                           IRepositoryContract.Repository repository,
                           ISchedulerProviderContract schedulerProvider,
                           CompositeDisposable disposable) {
        this.view = view;
        this.viewModel = viewModel;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = disposable;
    }


    @Override
    public void onStart(String title) {
        viewModel.setViewData(title);
        view.setViewData(viewModel.getViewData());
    }


    @Override
    public void addItem() {
        disposable.add(repository.addItem(new Item(title))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.setViewData(title);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }
                }));
    }


    @Override
    public void onDestroy() {
        disposable.clear();
    }
}
