package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.util.ISchedulerProviderContract;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Can safely rotate the device
 */
public class ItemListLogic implements IViewContract.Logic {

    private final IViewContract.View view;
    private final IViewContract.ViewModel viewModel;
    private final IRepositoryContract.Repository repository;
    private final ISchedulerProviderContract schedulerProvider;
    private final CompositeDisposable disposable;

    ItemListLogic(IViewContract.View view,
                  IViewContract.ViewModel viewModel,
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
                view.setIiStateError(viewModel.getMsgError());
                // Need to remove for JUnit testing.
//                Timber.e(t);
            }

            @Override
            public void onComplete() {
//                Timber.i("onComplete()");
            }
        };
    }

    private void renderView() {
        List<Item> itemList = viewModel.getViewData();
        if (itemList.isEmpty()) {
            view.setIiStateError(viewModel.getMsgEmptyList());
        } else {
            view.setList(itemList);
            view.setUiStateDisplayList();
        }
    }


    @Override
    public void onItemClicked(String title) {
        view.openDetailView(title);
    }


    /**
     * ViewModel reference is being held onto, that should cause a memory leak.
     * <p>
     * I am not nulling the reference, yet I have no leaks per LeakCanary.
     * I assume that is because the ViewModel is persisting.
     * <p>
     * CANNOT null the reference because this will called when the View is simply
     * paused (moved to background).
     */
    @Override
    public void onDestroy() {
        disposable.clear();
    }
}
