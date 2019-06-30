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
    private String errorMessage = "error";


    @InjectMocks
    private ItemListLogic logic;


    /**
     * Trampoline: a Scheduler that queues work on the current thread to be
     * executed after the current work completes.
     * Another way to put it: emits result in a sequentially predictable order.
     *
     * Because this is a unit test that is running on the JVM,
     * all operations run on the same thread the tests are running on.
     * Otherwise, an error in thrown by the Observable.
     */
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

    /**
     * When an empty List of Items is returned by the Repository,
     * the UI should display an error with a message indicating that the
     * list is empty.
     *
     * Because all user facing messages are stored in res/strings, I am supplying
     * my own.
     */
    @Test
    public void onStartEmptyList() {
        List<Item> emptyList = new ArrayList<>();

        when(repository.observe()).thenReturn(Flowable.just(emptyList));
        when(viewModel.getViewData()).thenReturn(emptyList);
        when(viewModel.getMsgEmptyList()).thenReturn(errorMessage);

        logic.onStart();

        verify(view).setUiStateLoading();
        verify(viewModel).setViewData(emptyList);
        verify(viewModel).getViewData();
        verify(view).setUiStateError(errorMessage);
    }

    /**
     * If the Flowable returned by the Repository throws an error,
     * the View should display an error.
     *
     * If this was a non-practice app, I would forward the Exception
     * to a util class that would either throw the Exception is build is DEBUG, else,
     * report it to Crashlytics.
     */
    @Test
    public void onStartRepositoryThrowsAnError() {
        Exception exception = new Exception(errorMessage);
        when(repository.observe()).thenReturn(Flowable.error(exception));
        when(viewModel.getMsgError()).thenReturn(errorMessage);

        logic.onStart();

        verify(view).setUiStateError(errorMessage);
    }


    @Test
    public void onItemClickedNormalTitle() {
        logic.onItemClicked(title);
        verify(view).openDetailView(title);
    }

    /**
     * An empty title should behave the same a non-empty title.
     */
    @Test
    public void onItemClickedEmptyTitle() {
        String emptyTitle = "";
        logic.onItemClicked(emptyTitle);
        verify(view).openDetailView(emptyTitle);
    }

    /**
     * A null title should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void onItemClickedNullTitle() {
        logic.onItemClicked(null);
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