package com.e.android_lab3;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class UtilitiesTest {

    private String language;
    Utilities util;

    @Before
    public void setUp()  {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        util = new Utilities(appContext);
        // get system language
        language = Locale.getDefault().getLanguage();
    }

    /**
     * tests the formatting method
     * checks length and region separator(./,)
     */
    @Test
    public void format() {

        double num = 1.2342566;
        String expected;
        String notExpected;
        String result = util.format(num);

        // if language is Norwegian (bokmaal)
        if (language.equalsIgnoreCase("nb")) {
            expected = "1,23";
            notExpected = "1.234";
        } else {
            expected = "1.23";
            notExpected = "1,234";
        }

        assertEquals(expected,result);
        assertNotEquals(notExpected,result);
    }
}