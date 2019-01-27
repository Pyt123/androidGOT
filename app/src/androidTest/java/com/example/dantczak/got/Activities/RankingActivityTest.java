package com.example.dantczak.got.Activities;


import android.content.Context;
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
    private String backupStartDate;
    private String backupEndDate;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp()
    {
        Context context = getInstrumentation().getTargetContext();
        TinyDb tinyDb = new TinyDb(getInstrumentation().getTargetContext());
        backupStartDate = context.getResources().getString(R.string.ranking_start_date);
        backupEndDate = context.getResources().getString(R.string.ranking_end_date);

        tinyDb.clear();
        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }

    @After
    public void tearDown()
    {
        Context context = getInstrumentation().getTargetContext();
        TinyDb tinyDb = new TinyDb(context);
        tinyDb.putString(context.getResources().getString(R.string.ranking_start_date), backupStartDate);
        tinyDb.putString(context.getResources().getString(R.string.ranking_end_date), backupEndDate);
    }

    @Test
    public void rankingActivityTest() {
        gotoRankingActivity();
        gotoPrzedzialDatActivity();

        // asserts
        onView(withId(R.id.start_date)).check(matches(not(ViewMatchers.isClickable())));
        onView(withId(R.id.end_date)).check(matches(not(ViewMatchers.isClickable())));
        //------------------------------------------------------------------------------------------

        ignoreStartDateTap();
        ignoreEndDateTap();

        // asserts
        onView(withId(R.id.start_date)).check(matches(ViewMatchers.isClickable()));
        onView(withId(R.id.end_date)).check(matches(ViewMatchers.isClickable()));
        //------------------------------------------------------------------------------------------

        confirmChanges();

        // save the current date
        String currentDate = sdf.format(java.util.Calendar.getInstance().getTime());

        ViewInteraction textView = onView(
                allOf(withId(R.id.rank_heading),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        // asserts
        textView.check(matches(withText("Ranking od 01-01-2000 do " + currentDate)));
        //------------------------------------------------------------------------------------------

        gotoPrzedzialDatActivity();

        // asserts
        onView(withId(R.id.start_date)).check(matches(ViewMatchers.isClickable()));
        onView(withId(R.id.end_date)).check(matches(ViewMatchers.isClickable()));
        //------------------------------------------------------------------------------------------

        ignoreStartDateTap();
        ignoreEndDateTap();

        // asserts
        onView(withId(R.id.start_date)).check(matches(not(ViewMatchers.isClickable())));
        onView(withId(R.id.end_date)).check(matches(not(ViewMatchers.isClickable())));
        //------------------------------------------------------------------------------------------


        pressBack();            // nothing should be saved

        textView = onView(
                allOf(withId(R.id.rank_heading),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        // asserts
        textView.check(matches(withText(("Ranking od 01-01-2000 do " + currentDate))));
        //------------------------------------------------------------------------------------------
    }

    private void confirmChanges() {
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.confirm_button), withText("Potwierdź"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());
    }


    private void ignoreStartDateTap() {
        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.ignore_start_date), withText("Ignoruj datę początkową"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatCheckBox.perform(click());
    }

    private void ignoreEndDateTap() {
        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.ignore_end_date), withText("Ignoruj datę końcową"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                10),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());
    }

    private void gotoRankingActivity() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.ranking_button), withText("Ranking"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());
    }

    private void gotoPrzedzialDatActivity() {
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.daty_button), withText("Przedział dat"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());
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
