package com.example.modelviewwhateverpractice.itemlist;

import androidx.lifecycle.ViewModel;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

final class ItemListViewModel extends ViewModel
        implements IViewContract.ViewModel {

    // Still using redundant Flowables.
    private Flowable<List<Item>> itemList;
    private final CompositeDisposable disposable;

    private IRepositoryContract.Repository repository;

    /**
     * I could init the query in the constructor in the cases where the ViewModel doesn't need
     * anything to properly query - this ensures the latest data is ready for the Logic class
     * as soon as possible. In the cases where the ViewModel does need something, I'll have a
     * method with a parameter.
     * <p>
     * OTOH, to keep things the same across multiple ViewModels, have a method with a parameter regardless.
     * <p>
     * OR, I could have the constructor contain the needed something - because this ViewModel extends
     * JetPack's ViewModel library, it will be created by a ViewModelFactory, thus I can pass the needed
     * thing in - OR not, if it is not required.
     * <p>
     * The last option is best.
     */
    ItemListViewModel(IRepositoryContract.Repository repository) {
        this.repository = repository;
        disposable = new CompositeDisposable();

        initThisFlowable();
        initTheDb();
    }

    private void initThisFlowable() {
        itemList = Flowable.create(
                this::flowableEmitter,
                BackpressureStrategy.BUFFER
        );
    }

    /**
     * This feels redundant - rethink ViewModel talking to Repository.
     */
    private void flowableEmitter(FlowableEmitter<List<Item>> emitter) {
        disposable.add(repository.observe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(itemSubscriber(emitter))
        );
    }

    /**
     * This might not work how I assumed - this work how I normally do things because the ViewModel
     * would persist and update a LiveData field, that would automatically query new values.
     */
    private DisposableSubscriber<List<Item>> itemSubscriber(FlowableEmitter<List<Item>> emitter) {
        // When Logic clears its Disposable, this Disposable need to be cleared as well,
        // otherwise a memory leak will occur.
        emitter.setCancellable(disposable::clear);

        return new DisposableSubscriber<List<Item>>() {
            @Override
            public void onNext(List<Item> items) {
                emitter.onNext(items);
            }

            @Override
            public void onError(Throwable t) {
                emitter.onError(t);
            }

            @Override
            public void onComplete() {
                emitter.onComplete();
            }
        };
    }


    @Override
    public Flowable<List<Item>> subscribe() {
        return itemList;
//        return repository.observe();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }


    private void initTheDb() {
        List<Item> items = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            items.add(new Item(String.valueOf(x)));
        }
        disposable.add(repository.addItems(items)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Timber.i("LIST INSERT WORK");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "LIST INSERT DID NOT WORK");
                    }
                })
        );
    }
}
