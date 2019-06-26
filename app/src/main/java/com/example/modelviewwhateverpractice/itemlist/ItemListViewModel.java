package com.example.modelviewwhateverpractice.itemlist;

import androidx.lifecycle.ViewModel;

import com.example.modelviewwhateverpractice.datamodel.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

final class ItemListViewModel extends ViewModel
        implements IViewContract.ViewModel {

    private Flowable<List<Item>> itemList;

    /**
     * I could init the query in the constructor in the cases where the ViewModel doesn't need
     * anything to properly query - this ensures the latest data is ready for the Logic class
     * as soon as possible. In the cases where the ViewModel does need something, I'll have a
     * method with a parameter.
     *
     * OTOH, to keep things the same across multiple ViewModels, have a method with a parameter regardless.
     *
     * OR, I could have the constructor contain the needed something - because this ViewModel extends
     * JetPack's ViewModel library, it will be created by a ViewModelFactory, thus I can pass the needed
     * thing in - OR not, if it is not required.
     *
     * The last option is best.
     */
    public ItemListViewModel() {
        this.itemList = Flowable.create(
                this::flowableEmitter,
                BackpressureStrategy.BUFFER
        );
    }

    private void flowableEmitter(FlowableEmitter<List<Item>> emitter) {
        List<Item> fromRepository = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            fromRepository.add(new Item(String.valueOf(x)));
        }
        emitter.onNext(fromRepository);
    }


    @Override
    public Flowable<List<Item>> subscribe() {
        return itemList;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
