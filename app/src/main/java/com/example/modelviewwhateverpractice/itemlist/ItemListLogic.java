package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * Can safely rotate the device
 */
public class ItemListLogic implements IViewContract.Logic {

    private final IViewContract.View view;
    private IViewContract.ViewModel viewModel;
    private final IRepositoryContract.Repository repository;

    private CompositeDisposable disposable;

    ItemListLogic(IViewContract.View view,
                  IViewContract.ViewModel viewModel,
                  IRepositoryContract.Repository repository) {
        this.view = view;
        this.viewModel = viewModel;
        this.repository = repository;
        this.disposable = new CompositeDisposable();

        addToRepository();
    }

    private void addToRepository() {
        disposable.add(repository.addItem(new Item("Added from Logic"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Timber.i("Adding from Logic worked");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }
                })
        );
    }


    @Override
    public void onStart() {
        view.uiStateLoading();

        disposable.add(repository.observe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                view.uiStateError(viewModel.getMsgError());
                // Need to remove for JUnit testing.
                Timber.e(t);
            }

            @Override
            public void onComplete() {
                Timber.i("onComplete()");
            }
        };
    }

    private void renderView() {
        view.setList(viewModel.getViewData());
        if (viewModel.getViewData().isEmpty()) {
            view.uiStateError(viewModel.getMsgEmptyList());
        } else {
            view.uiStateDisplayList();
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
