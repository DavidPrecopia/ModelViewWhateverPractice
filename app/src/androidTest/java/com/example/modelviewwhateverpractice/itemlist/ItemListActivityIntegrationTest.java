package com.example.modelviewwhateverpractice.itemlist;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.common.buildlogic.CommonViewModule;
import com.example.modelviewwhateverpractice.util.SchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * To be used with the mock flavor because {@link CommonViewModule}
 * returns an in-memory database.
 *
 * This test always failed at first because I am running in the mock flavor
 * and I had a mock {@link SchedulerProvider}.
 * Removing it from mock and prod and back to main source set fixed everything.
 */
@RunWith(AndroidJUnit4.class)
public class ItemListActivityIntegrationTest {

    @Before
    public void init() {
        FragmentScenario.launchInContainer(
                ItemListView.class,
                null,
                R.style.Theme_MaterialComponents_Light_DarkActionBar,
                null
        );
    }


    @Test
    public void testAddingAnItem() {
        String newItemTitle = ItemListLogic.NEW_ITEM_TITLE;

        onView(withId(R.id.fab_add_list))
                .perform(click());

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(newItemTitle))));

        onView(withText(newItemTitle))
                .check(matches(isDisplayed()));
    }
}