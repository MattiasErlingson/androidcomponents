package com.example.form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.form.MainActivity;
import com.example.form.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;



import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.form", appContext.getPackageName());
    }

    @Test
    public  void checkPasswordField(){
        onView(withId(R.id.passwordField)).perform(replaceText("abc"));
        onView(withId(R.id.descriptionField)).check(matches(withText("Password must be atleast 6 long.")));

        onView(withId(R.id.passwordField)).perform(replaceText("abcdef"));
        onView(withId(R.id.descriptionField)).check(matches(withText("Password is weak")));

        onView(withId(R.id.passwordField)).perform(replaceText("abcdef1"));
        onView(withId(R.id.descriptionField)).check(matches(withText("Password is decent")));

        onView(withId(R.id.passwordField)).perform(replaceText("abcdefG"));
        onView(withId(R.id.descriptionField)).check(matches(withText("Password is decent")));

        onView(withId(R.id.passwordField)).perform(replaceText("abcdefG1"));
        onView(withId(R.id.descriptionField)).check(matches(withText("Password is STRONG")));
    }

    @Test
    public void checkToast() throws InterruptedException {

        String notValid = "Please fill out remaining fields";
        String valid = "Success";
        MainActivity activity = mainActivityActivityTestRule.getActivity();

        onView(withId(R.id.name)).perform(replaceText("Mattias"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withText(notValid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Thread.sleep(5000);

        onView(withId(R.id.surname)).perform(replaceText("Erlingson"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withText(notValid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Thread.sleep(5000);

        onView(withId(R.id.email)).perform(replaceText("mattias.erlingson@gmail.com"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withText(notValid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Thread.sleep(5000);

        onView(withId(R.id.passwordField)).perform(replaceText("abcdeF5"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withText(valid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        Thread.sleep(5000);

    }

}