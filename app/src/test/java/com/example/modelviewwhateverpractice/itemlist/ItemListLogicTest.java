package com.example.modelviewwhateverpractice.itemlist;

import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.util.ISchedulerProviderContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemListLogicTest {

    @Mock
    private IViewContract.View view;

    @Mock
    private IViewContract.ViewModel viewModel;

    @Mock
    private IRepositoryContract.Repository repository;

    @Mock
    private ISchedulerProviderContract schedulerProvider;

    private CompositeDisposable disposable = spy(new CompositeDisposable());

    private String title = "item title";


    @InjectMocks
    private ItemListLogic logic;


    @Before
    public void init() {
        when(schedulerProvider.io()).thenReturn(Schedulers.trampoline());
        when(schedulerProvider.ui()).thenReturn(Schedulers.trampoline());
    }


    /**
     * A List of Items is retrieved from the Repository and given to the View.
     */
    @Test
    public void onStartNormalBehavior() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(0, title));

        when(repository.observe()).thenReturn(Flowable.just(itemList));
        when(viewModel.getViewData()).thenReturn(itemList);

        logic.onStart();

        verify(view).setUiStateLoading();
        verify(viewModel).setViewData(itemList);
        verify(viewModel).getViewData();
        verify(view).setList(itemList);
        verify(view).setUiStateDisplayList();
    }

    @Test
    public void onItemClicked() {
        logic.onItemClicked(title);
        verify(view).openDetailView(title);
    }

    /**
     * Verify that CompositeDisposable is cleared when the View is destroyed.
     */
    @Test
    public void disposablesCleared() {
        logic.onDestroy();
        verify(disposable).clear();
    }
}