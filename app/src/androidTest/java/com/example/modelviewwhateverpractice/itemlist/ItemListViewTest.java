package com.example.modelviewwhateverpractice.itemlist;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.modelviewwhateverpractice.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ItemListViewTest {

    private FragmentScenario<ItemListView> itemListView;

    @Before
    public void init() {
        itemListView = FragmentScenario.launchInContainer(
                ItemListView.class,
                null,
                // Without this, the FAB will encounter an error when inflating.
                R.style.Theme_MaterialComponents_Light_DarkActionBar,
                null
        );
    }


    @Test
    public void testUiLoadingState() {
        itemListView.onFragment(ItemListView::setUiStateLoading);

        onView(withId(R.id.progress_bar))
                .check(matches(isDisplayed()));


        onView(withId(R.id.recycler_view))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void testUiDisplayListState() {
        itemListView.onFragment(ItemListView::setUiStateDisplayList);

        onView(withId(R.id.progress_bar))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
    }
}