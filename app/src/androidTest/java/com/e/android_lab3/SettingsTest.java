package com.e.android_lab3;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
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
    public void setUp()  {
        // get system language
        language = Locale.getDefault().getLanguage();
    }

    /**
      tests if the right strings are being used for different languages
      for the textView: min_acc_textView
     */
    @Test
    public void testTextField_minAccTextView() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.min_acc_textView)).check(matches(isDisplayed()));

        // if language is Norwegian (bokmaal)
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


    /**
     * tests if the right strings are being used for different languages
     * for the textView: sliding_window_textView
     */
    @Test
    public void testTextField_slidingWindowTextView() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.sliding_window_textView)).check(matches(isDisplayed()));

        // if language is Norwegian (bokmaal)
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



   /**
     * tests if the right strings are being used for different languages
     * for the textView: select_background_textView
     */
    @Test
    public void testTextField_select_background_textView() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.select_background_textView)).check(matches(isDisplayed()));

        // if language is Norwegian (bokmaal)
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




    /**
     * tests if the right strings are being used for different languages
     * for the button: reset_highscore_button
     */
    @Test
    public void testTextField_resetHighscoreButton() {
        // close keyboard
        Espresso.closeSoftKeyboard();
        // find and check right view on screen
        Espresso.onView(withId(R.id.reset_highscore_button)).check(matches(isDisplayed()));

        // if language is Norwegian (bokmaal)
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


    /**
     * testes the resetHighscorButton
     */
    @Test
    public void resetHighscorButtonClick() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utilities util = new Utilities(appContext);

        // find and click the right view on screen
        Espresso.onView(withId(R.id.reset_highscore_button)).perform(ViewActions.click());

        String highScoreKey= "heightScore";
        float expected = 0.00f;
        float actual = util.getPreferenceFloat(highScoreKey);

        assertEquals(expected,actual,0.00f);
    }
}