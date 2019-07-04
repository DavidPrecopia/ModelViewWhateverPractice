package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.util.ISchedulerProviderContract;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * Can safely rotate the device.
 */
public class ItemListLogic implements IListViewContract.Logic {

    // Practice testing reasons.
    static final String NEW_ITEM_TITLE = "ADDED FROM LIST";

    private final IListViewContract.View view;
    private final IListViewContract.ViewModel viewModel;
    private final IRepositoryContract.Repository repository;
    private final ISchedulerProviderContract schedulerProvider;
    private final CompositeDisposable disposable;

    ItemListLogic(IListViewContract.View view,
                  IListViewContract.ViewModel viewModel,
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
    public void onStart() {
        view.setUiStateLoading();

        disposable.add(repository.observe()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(itemSubscriber())
        );
    }

    private DisposableSubscriber<List<Item>> itemSubscriber() {
        return new DisposableSubscriber<List<Item>>() {
            @Override
            public void onNext(List<Item> items) {
                viewModel.setViewData(items);
                renderView();
            }

            @Override
            public void onError(Throwable t) {
                view.setUiStateError(viewModel.getMsgError());
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void renderView() {
        List<Item> itemList = viewModel.getViewData();
        if (itemList.isEmpty()) {
            view.setUiStateError(viewModel.getMsgEmptyList());
        } else {
            view.setList(itemList);
            view.setUiStateDisplayList();
        }
    }


    @Override
    public void onItemClicked(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        view.openDetailView(title);
    }

    @Override
    public void addItem() {
        disposable.add(repository.addItem(new Item(NEW_ITEM_TITLE))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Timber.i("ON COMPLETE");
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
