package pl.wsei.mobilne.reminderapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EventAdapterTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkEventItemDisplay() {
        // Add your events data to the eventList before running the test.

        onView(withId(R.id.event_item_name)) // replace with your RecyclerView id
                .perform(RecyclerViewActions.scrollToPosition(0)) // scroll to the first item in the list
                .check(matches(hasDescendant(withText("Event")))); // replace with the expected event name

        // Add similar checks for other fields such as event_item_date, event_item_time, etc.
    }
    @Test
    public void checkEventDateInView() {
        onView(withId(R.id.event_item_date)) // replace with actual recycler view id
                .perform(RecyclerViewActions.scrollToPosition(0)) // scroll to the position of the event
                .check(matches(hasDescendant(withText("2023-05-31")))); // check if the date of the event matches the expected date
    }

    @Test
    public void checkEventDEscInView() {
        onView(withId(R.id.event_item_description)) // replace with actual recycler view id
                .perform(RecyclerViewActions.scrollToPosition(0)) // scroll to the position of the event
                .check(matches(hasDescendant(withText("xxx")))); // check if the date of the event matches the expected date
    }

}