package com.e.android_lab3;

import android.app.Activity;
import android.content.res.Resources;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

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


    private String language;
    @Before
    public void setUp() throws Exception {
        // get system language
        language = Locale.getDefault().getLanguage();
    }

    /**
     * tests if the right strings are being used for different languages
     * for the textView: time_in_air
     */
    @Test
    public void testTextField_TimeInAir() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.time_in_air)).check( matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "Tid (Sec)";
            String wrong = "Tie ";
            Espresso.onView(withId(R.id.time_in_air)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.time_in_air)).check((matches(not(withText(wrong)))));

        } else {
            String right = "Time (Sec)";
            String wrong = "Tie: ";
            Espresso.onView(withId(R.id.time_in_air)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.time_in_air)).check((matches(not(withText(wrong)))));
        }
    }

    /**
     * tests if the right strings are being used for different languages
     * for the textView: highscore
     */
    @Test
    public void testTextField_highscore() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.highscore)).check(matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "Høyeste kast (M)";
            String wrong = "Høyesteste kast (M)";
            Espresso.onView(withId(R.id.highscore)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.highscore)).check((matches(not(withText(wrong)))));

        } else {
            String right = "Highscore (M)";
            String wrong = "Highscoreing (M)";
            Espresso.onView(withId(R.id.highscore)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.highscore)).check((matches(not(withText(wrong)))));
        }
    }


    /**
     * tests if the right strings are being used for different languages
     * for the textView: acc
     */
    @Test
    public void testTextField_acc() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.acc)).check(matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "Akselerasjon:";
            String wrong = "Acceleration:";
            Espresso.onView(withId(R.id.acc)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.acc)).check((matches(not(withText(wrong)))));

        } else {
            String right = "Acceleration:";
            String wrong = "Akselerasjon:";
            Espresso.onView(withId(R.id.acc)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.acc)).check((matches(not(withText(wrong)))));
        }
    }

/*

    *//**
     * tests if the right strings are being used for different languages
     * for the textView: acc_sliding_window
     */
    @Test
    public void testTextField_accSlidingWindow() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.acc_sliding_window)).check(matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "Sliding Window maksverdi:";
            String wrong = "Sliding Window max:";
            Espresso.onView(withId(R.id.acc_sliding_window)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.acc_sliding_window)).check((matches(not(withText(wrong)))));

        } else {
            String right = "Sliding Window max:";
            String wrong = "Sliding Window maksverdi:";
            Espresso.onView(withId(R.id.acc_sliding_window)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.acc_sliding_window)).check((matches(not(withText(wrong)))));
        }
    }




}