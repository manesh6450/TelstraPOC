package com.wipro.telstrapoc.view;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.wipro.telstrapoc.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.wipro.telstrapoc", appContext.getPackageName());
    }

    @Test
    public void scrollToPosition() {
        Espresso.onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(0));
    }

    @Test
    public void refreshView() {
        Espresso.onView(withId(R.id.simpleSwipeRefreshLayout)).perform(swipeDown());
    }

    @Test
    public void titleBarTitle() {
        Espresso.onView(allOf(
                isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(isDisplayed()));
    }
}