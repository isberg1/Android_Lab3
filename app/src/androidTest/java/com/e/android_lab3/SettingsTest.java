package com.e.android_lab3;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Locale;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class SettingsTest {


    @Rule
    public ActivityTestRule<Settings> activityTestRule= new ActivityTestRule<Settings>(Settings.class);


    private String language;
    @Before
    public void setUp() throws Exception {
        // get system language
        language = Locale.getDefault().getLanguage();
    }

    /*
      tests if the right strings are being used for different languages
      for the textView: min_acc_textView
     */
    @Test
    public void testTextField_minAccTextView() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.min_acc_textView)).check(matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "MIN_ACC VERDI";
            String wrong = "MIN_ACC VALUE";
            Espresso.onView(withId(R.id.min_acc_textView)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.min_acc_textView)).check((matches(not(withText(wrong)))));

        } else {
            String right = "MIN_ACC VALUE";
            String wrong = "MIN_ACC VERDI";
            Espresso.onView(withId(R.id.min_acc_textView)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.min_acc_textView)).check((matches(not(withText(wrong)))));
        }
    }


    /* *
     * tests if the right strings are being used for different languages
     * for the textView: sliding_window_textView
     */
    @Test
    public void testTextField_slidingWindowTextView() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.sliding_window_textView)).check(matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "SLIDING_WINDOW STØRRELSE";
            String wrong = "SLIDING_WINDOW SIZE";
            Espresso.onView(withId(R.id.sliding_window_textView)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.sliding_window_textView)).check((matches(not(withText(wrong)))));

        } else {
            String right = "SLIDING_WINDOW SIZE";
            String wrong = "SLIDING_WINDOW STØRRELSE";
            Espresso.onView(withId(R.id.sliding_window_textView)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.sliding_window_textView)).check((matches(not(withText(wrong)))));
        }
    }



    /*
     *//**
     * tests if the right strings are being used for different languages
     * for the textView: select_background_textView
     */
    @Test
    public void testTextField_select_background_textView() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.select_background_textView)).check(matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "VELG BAKGRUNNSBILDE";
            String wrong = "SELECT BACKGROUND";
            Espresso.onView(withId(R.id.select_background_textView)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.select_background_textView)).check((matches(not(withText(wrong)))));

        } else {
            String right = "SELECT BACKGROUND";
            String wrong = "VELG BAKGRUNNSBILDE";
            Espresso.onView(withId(R.id.select_background_textView)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.select_background_textView)).check((matches(not(withText(wrong)))));
        }
    }




    /*
     *//**
     * tests if the right strings are being used for different languages
     * for the button: reset_highscore_button
     */
    @Test
    public void testTextField_resetHighscoreButton() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.reset_highscore_button)).check(matches(isDisplayed()));

        // if language is Norwegian
        if (language.equalsIgnoreCase("nb")) {
            String right = "RESET TOPPSCOREN";
            String wrong = "RESET HIGHSCORE";
            Espresso.onView(withId(R.id.reset_highscore_button)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.reset_highscore_button)).check((matches(not(withText(wrong)))));

        } else {
            String right = "RESET HIGHSCORE";
            String wrong = "RESET TOPPSCOREN";
            Espresso.onView(withId(R.id.reset_highscore_button)).check(matches(withText(right)));
            Espresso.onView(withId(R.id.reset_highscore_button)).check((matches(not(withText(wrong)))));
        }
    }



    @Test
    public void onCreate() {
    }

    @Test
    public void findViews() {
    }

    @Test
    public void resetHighscorButtonClick() {
    }
}