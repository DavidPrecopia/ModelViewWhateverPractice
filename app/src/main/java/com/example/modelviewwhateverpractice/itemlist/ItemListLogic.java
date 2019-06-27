package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

public class ItemListLogic implements IViewContract.Logic {

    private final IViewContract.View view;
    private IViewContract.ViewModel viewModel;

    private CompositeDisposable disposable;

    ItemListLogic(IViewContract.View view, IViewContract.ViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
        this.disposable = new CompositeDisposable();
        Timber.e("LOGIC CONSTRUCTOR");
    }


    @Override
    public void onStart() {
        view.uiStateLoading();

        disposable.add(viewModel.subscribe()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(itemSubscriber())
        );
    }

    private DisposableSubscriber<List<Item>> itemSubscriber() {
        return new DisposableSubscriber<List<Item>>() {
            @Override
            public void onNext(List<Item> items) {
                view.setList(items);
                view.uiStateDisplayList();
            }

            @Override
            public void onError(Throwable t) {
                view.uiStateError("Error message");
                // Need to remove for JUnit testing.
                Timber.e(t);
            }

            @Override
            public void onComplete() {
                Timber.i("onComplete()");
            }
        };
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
    public void onPause() {
        disposable.clear();
    }
}
