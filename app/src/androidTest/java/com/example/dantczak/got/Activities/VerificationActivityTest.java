package com.example.dantczak.got.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.dantczak.got.DTO.Pair;
import com.example.dantczak.got.DTO.PathToVerify;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.TinyDb;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class VerificationActivityTest {

    private final String TEST_IP_AND_PORT = "192.168.1.15:21037";
    private String ipAndPortKey;
    private String backupIpAndPort;

    @Rule
    public ActivityTestRule<VerificationActivity> mActivityTestRule = new ActivityTestRule<>(VerificationActivity.class, false, false);

    @Before
    public void setUp() {
        Context context = getInstrumentation().getTargetContext();
        TinyDb tinyDb = new TinyDb(context);
        ipAndPortKey = context.getResources().getString(R.string.ip_key);
        backupIpAndPort = tinyDb.getString(ipAndPortKey);
        tinyDb.putString(ipAndPortKey, TEST_IP_AND_PORT);

        PathToVerify pathToVerify = new PathToVerify(1L, 1L, "Jan", "Kowalski",
                new Date(0), new ArrayList<Pair<String, String>>(0), new ArrayList<byte[]>(0),
                5, true);
        VerificationActivity.setPathToVerifyInstance(pathToVerify);
    }

    @After
    public void tearDown() {
        Context context = getInstrumentation().getTargetContext();
        TinyDb tinyDb = new TinyDb(context);
        tinyDb.putString(ipAndPortKey, backupIpAndPort);
    }

    @Test
    public void verificationActivityTest() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);


        onView(withId(R.id.zatwierdz_button)).perform(click());

        // asserts ---------------------------------------------------------------------------------
        onView(withId(android.R.id.text1)).check(matches(withText(
                VerificationActivity.getPathToVerifyInstance().getRankPointsFor().toString()))
        );
        onView(withText("Potwierdź")).inRoot(RootMatchers.isDialog()).check(matches(isEnabled()));
        //------------------------------------------------------------------------------------------

        onView(withId(android.R.id.text1)).perform(click());
        onView(withId(android.R.id.text1)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_DEL));

        // asserts ---------------------------------------------------------------------------------
        onView(withId(android.R.id.text1)).inRoot(RootMatchers.isDialog()).check(matches(withText("")));
        onView(withText("Potwierdź")).inRoot(RootMatchers.isDialog()).check(matches(not(isEnabled())));
        //------------------------------------------------------------------------------------------

        onView(withId(android.R.id.text1)).perform(typeText("0"));

        // asserts ---------------------------------------------------------------------------------
        onView(withId(android.R.id.text1)).inRoot(RootMatchers.isDialog()).check(matches(withText("")));
        onView(withText("Potwierdź")).inRoot(RootMatchers.isDialog()).check(matches(not(isEnabled())));
        //------------------------------------------------------------------------------------------

        onView(withId(android.R.id.text1)).perform(typeText("11"));

        // asserts ---------------------------------------------------------------------------------
        onView(withId(android.R.id.text1)).check(matches(withText("11")));
        onView(withText("Potwierdź")).inRoot(RootMatchers.isDialog()).check(matches(isEnabled()));
        //------------------------------------------------------------------------------------------
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
