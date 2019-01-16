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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class ChangeProfileDetailsTest {
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
        String birthday = "02/21/1994";
        String lastName = "Max";
        onView(withId(R.id.btnAccountSettings))
                .perform(click());

        onView(withId(R.id.lastNameEditText))
                .check(matches(isDisplayed()));

        onView(withId(R.id.lastNameEditText))
                .perform(click());

        onView(withId(R.id.lastNameEditText))
                .perform(typeText(lastName), closeSoftKeyboard());

        onView(withId(R.id.birthdayEditText))
                .perform(replaceText(birthday), closeSoftKeyboard());

        onView(withId(R.id.btnSave))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btnSave))
                .perform(click());

    }
}

