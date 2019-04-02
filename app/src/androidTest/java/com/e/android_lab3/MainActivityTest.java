package com.e.android_lab3;

import android.app.Activity;
import android.content.res.Resources;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule= new ActivityTestRule<MainActivity>(MainActivity.class);

    private String name = "name";

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testTextField() {


        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.highscore)).check( matches(isDisplayed()));


        String right = "Time: ";
        String wrong = "Tie: ";
        Espresso.onView(withId(R.id.time_in_air)).check(matches(withText(right)));
        Espresso.onView(withId(R.id.time_in_air)).check((matches(not(withText(wrong)))));
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }
}