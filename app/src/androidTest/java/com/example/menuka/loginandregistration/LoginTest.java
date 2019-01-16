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

/**
 * Created by menuka on 3/30/17.
 */

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Before
    public void fireActivity(){
        Intent i = new Intent();
        loginActivityActivityTestRule.launchActivity(i);
        onView(withId(R.id.btnSignOut))
                .perform(click());
    }

    // login with nothing entered
    // should stay in the same activity
    @Test
    public void clickLoginButton_validatesInputs() throws Exception{
        // clicking register without filling details
        onView(withId(R.id.btn_login))
                .perform(click());
        onView(withId(R.id.btn_login))
                .check(matches(isDisplayed()));
    }

    // login with wrong credentials
    // should stay in the same activity
    @Test
    public void loginWithWrongCredentials() throws Exception{
        String email = "harry@gmail.com"; // registered email
        String password = "23234342";   // wrong password

        // enter email
        onView(withId(R.id.email_edit_text_login))
                .perform(typeText(email), closeSoftKeyboard());
        // enter password
        onView(withId(R.id.password_edit_text_login))
                .perform(typeText(password), closeSoftKeyboard());

        // click login
        onView(withId(R.id.btn_login))
                .perform(click());

        onView(withId(R.id.btn_login))
                .check(matches(isDisplayed()));

    }

    @Test
    public void loginWithCorrectCredentials() throws Exception{
        String email = "harry@gmail.com";
        String password = "123456";

        // enter email
        onView(withId(R.id.email_edit_text_login))
                .perform(typeText(email), closeSoftKeyboard());

        // enter password
        onView(withId(R.id.password_edit_text_login))
                .perform(typeText(password), closeSoftKeyboard());

        // click sign in button
        onView(withId(R.id.sign_in_button))
                .perform(click());

        onView(withId(R.id.btnAccountSettings))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btnSignOut))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btnSignOut))
                .perform(click());
    }
}

