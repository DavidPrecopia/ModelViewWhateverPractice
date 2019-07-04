package com.example.modelviewwhateverpractice.itemdetail;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.modelviewwhateverpractice.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ItemDetailViewFragmentScenarioTest {

    private String ARG = ItemDetailView.ARG_KEY_TITLE;

    private final String title = "title";


    @Before
    public void init() {
        Bundle bundle = new Bundle();
        // This key <em>must be</em> the exact same as the key
        // used by the Fragment itself.
        bundle.putString(ARG, title);

        FragmentScenario.launchInContainer(
                ItemDetailView.class,
                bundle,
                // Without this, it will be unable to inflate an FAB
                R.style.Theme_MaterialComponents_Light_DarkActionBar,
                null
        );
    }


    @Test
    public void verifyCorrectTitleIsDisplayed() {
        onView(withId(R.id.tv_title))
                .check(matches(withText(title)));
    }

    @Test
    public void verifyAddingAnItemChangesTheDisplayedTitle() {
        String addedItemTitle = ItemDetailLogic.title;

        onView(withId(R.id.fab_add_detail))
                .perform(click());

        onView(withId(R.id.tv_title))
                .check(matches(withText(addedItemTitle)));
    }
}