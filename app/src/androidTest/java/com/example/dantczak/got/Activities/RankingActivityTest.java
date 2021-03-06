package com.example.dantczak.got.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.TinyDb;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Map;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RankingActivityTest {

    private SimpleDateFormat sdf;
    private String startDateKey;
    private String endDateKey;
    private String backupStartDate;
    private String backupEndDate;

    @Rule
    public ActivityTestRule<RankingActivity> mActivityTestRule = new ActivityTestRule<>(RankingActivity.class, false, false);

    @Before
    public void setUp()
    {
        Context context = getInstrumentation().getTargetContext();
        TinyDb tinyDb = new TinyDb(getInstrumentation().getTargetContext());
        startDateKey = context.getResources().getString(R.string.ranking_start_date);
        endDateKey = context.getResources().getString(R.string.ranking_end_date);
        backupStartDate = tinyDb.getString(startDateKey);
        backupEndDate = tinyDb.getString(endDateKey);
        tinyDb.remove(startDateKey);
        tinyDb.remove(endDateKey);

        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }

    @After
    public void tearDown()
    {
        Context context = getInstrumentation().getTargetContext();
        TinyDb tinyDb = new TinyDb(context);
        tinyDb.putString(startDateKey, backupStartDate);
        tinyDb.putString(endDateKey, backupEndDate);
    }

    @Test
    public void rankingActivityTest() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);

        // asserts ---------------------------------------------------------------------------------
        onView(withId(R.id.rank_heading)).check(matches(withText("Ranking")));
        //------------------------------------------------------------------------------------------

        onView(withId(R.id.daty_button)).perform(click());

        // asserts ---------------------------------------------------------------------------------
        onView(withId(R.id.start_date)).check(matches(not(ViewMatchers.isClickable())));
        onView(withId(R.id.end_date)).check(matches(not(ViewMatchers.isClickable())));
        //------------------------------------------------------------------------------------------

        onView(withId(R.id.ignore_start_date)).perform(click());
        onView(withId(R.id.ignore_end_date)).perform(click());

        // asserts ---------------------------------------------------------------------------------
        onView(withId(R.id.start_date)).check(matches(ViewMatchers.isClickable()));
        onView(withId(R.id.end_date)).check(matches(ViewMatchers.isClickable()));
        //------------------------------------------------------------------------------------------

        onView(withId(R.id.confirm_button)).perform(click());

        // save the current date
        String currentDate = sdf.format(java.util.Calendar.getInstance().getTime());

        // asserts ---------------------------------------------------------------------------------
        onView(withId(R.id.rank_heading)).check(matches(withText("Ranking od 01-01-2000 do " + currentDate)));
        //------------------------------------------------------------------------------------------

        onView(withId(R.id.daty_button)).perform(click());

        // asserts ---------------------------------------------------------------------------------
        onView(withId(R.id.start_date)).check(matches(ViewMatchers.isClickable()));
        onView(withId(R.id.end_date)).check(matches(ViewMatchers.isClickable()));
        //------------------------------------------------------------------------------------------

        onView(withId(R.id.ignore_start_date)).perform(click());
        onView(withId(R.id.ignore_end_date)).perform(click());

        // asserts ---------------------------------------------------------------------------------
        onView(withId(R.id.start_date)).check(matches(not(ViewMatchers.isClickable())));
        onView(withId(R.id.end_date)).check(matches(not(ViewMatchers.isClickable())));
        //------------------------------------------------------------------------------------------

        pressBack();            // nothing should be saved

        // asserts ---------------------------------------------------------------------------------
        onView(withId(R.id.rank_heading)).check(matches(withText(("Ranking od 01-01-2000 do " + currentDate))));
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
