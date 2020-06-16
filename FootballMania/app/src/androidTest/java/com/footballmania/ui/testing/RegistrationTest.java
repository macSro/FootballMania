package com.footballmania.ui.testing;


import com.footballmania.R;
import com.footballmania.ui.splash.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistrationTest {

    @Rule
    public IntentsTestRule<SplashActivity> mActivityTestRule = new IntentsTestRule<>(SplashActivity.class);

    @Test
    public void registerAndLoginTest() throws InterruptedException {
        String temp = "Marcelina";
        onView(withId(R.id.registerLabel)).perform(click());
        onView(withId(R.id.registerName)).perform(typeText(temp), closeSoftKeyboard());
        onView(withId(R.id.registerEmail)).perform(typeText(temp + "@wp.pl"), closeSoftKeyboard());
        onView(withId(R.id.registerLogin)).perform(typeText(temp), closeSoftKeyboard());
        onView(withId(R.id.registerPassword)).perform(typeText(temp), closeSoftKeyboard());
        onView(withId(R.id.registerPasswordConfirm)).perform(typeText(temp), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());

        pressBack();

        Thread.sleep(3000);
    }
}
