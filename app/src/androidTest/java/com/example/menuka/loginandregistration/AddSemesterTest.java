package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class AddSemesterTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void fireActivity() {
        Intent i = new Intent();
        loginActivityActivityTestRule.launchActivity(i);
    }


    @Test
    public void addSemesterTest(){
        String number = "8";
        String year = "2018";
        onView(withId(R.id.btnGradesManagement))
                .perform(click());

        onView(withId(R.id.btnAddSemester))
                .perform(click());

        onView(withId(R.id.numberEditText))
                .perform(typeText(number), closeSoftKeyboard());

        onView(withId(R.id.yearEditText))
                .perform(typeText(year), closeSoftKeyboard());

        onView(withId(R.id.btnAdd))
                .perform(click());
    }

}

