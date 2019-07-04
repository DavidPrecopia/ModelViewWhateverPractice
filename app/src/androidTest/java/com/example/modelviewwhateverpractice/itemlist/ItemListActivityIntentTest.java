package com.example.modelviewwhateverpractice.itemlist;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.itemdetail.ItemDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ItemListActivityIntentTest {

    // This is for practice purposes only.
    private String newItemTitle = ItemListLogic.NEW_ITEM_TITLE;

    @Rule
    public IntentsTestRule intentsTestRule = new IntentsTestRule<>(ItemListActivity.class);


    @Test
    public void verifyIntentUsedToOpenDetailView() {
        onView(withId(R.id.fab_add_list))
                .perform(click());

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // This is equivalent to verify() in Mockito.
        intended(allOf(
                hasExtra(ItemDetailActivity.INTENT_EXTRA_KEY, newItemTitle),
                hasComponent(ItemDetailActivity.class.getName())
        ));
    }
}