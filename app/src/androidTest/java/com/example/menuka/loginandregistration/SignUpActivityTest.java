package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.support.test.espresso.core.deps.guava.util.concurrent.ExecutionError;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by menuka on 3/30/17.
 */

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {
    private String mStringToBeTyped;
    // fire up this activity for testing
    @Rule
    public ActivityTestRule<SignupActivity> signupActivityActivityTestRule =
            new ActivityTestRule<>(SignupActivity.class);

    @Before
    public void initValidString() throws Exception{
        Intent i = new Intent();
        signupActivityActivityTestRule.launchActivity(i);

        mStringToBeTyped = "harry@gmail.com";
    }

    // input email only
//    @Test
    public void changeText_emailAddress(){
        // Type the email and press register
        onView(withId(R.id.email_edit_text))
                .perform(typeText(mStringToBeTyped), closeSoftKeyboard());
        onView(withId(R.id.sign_up_button)).perform(click());

        // should remain in the same activity since the password is not blank
        onView(withId(R.id.sign_up_button))
                .check(matches(isDisplayed()));
    }

    // input email and password (existing email and password for signing up)
//    @Test
    public void inputEmailAndPasswordTest(){
        String email = "harry@gmail.com";
        String password = "123456";
        // Type the email
        onView(withId(R.id.email_edit_text))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password_edit_text))
                .perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.email_edit_text))
                .check(matches(isDisplayed()));
    }

    // input new email and password (valid)
//    @Test
    public void registerNewStudentTest(){
        String email = "sheldon@gmail.com";
        String password = "abcdef";
    }

    @Test
    public void checkSignUpButton_validatesInputs() throws Exception{
        onView(withId(R.id.sign_up_button))
                .perform(click());
        onView(withId(R.id.email_edit_text))
                .check(matches(isDisplayed()));
    }

}
