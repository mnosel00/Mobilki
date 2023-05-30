package pl.wsei.mobilne.reminderapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(manifest=Config.NONE)
@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    @Test
    public void testToggleThemeSwitch() {
        try (ActivityScenario<SettingsActivity> scenario = ActivityScenario.launch(SettingsActivity.class)) {
            // Check initial state
            onView(withId(R.id.themeSwithc)).check(matches(not(isChecked())));
            onView(withId(R.id.setTheTheme)).check(matches(withText("Light")));

            // Click on the switch
            onView(withId(R.id.themeSwithc)).perform(click());

            // Check if switch is now checked and theme is set to Dark
            onView(withId(R.id.themeSwithc)).check(matches(isChecked()));
            onView(withId(R.id.setTheTheme)).check(matches(withText("Dark")));
        }
    }
}