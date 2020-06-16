package com.footballmania.ui.testing;

import com.footballmania.MainActivity;
import com.footballmania.R;
import com.footballmania.ui.splash.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShowArticleTest {

    @Rule
    public IntentsTestRule<SplashActivity> mActivityTestRule = new IntentsTestRule<>(SplashActivity.class);

    @Test
    public void showArticle() throws InterruptedException {
        onView(withId(R.id.loginEditText)).perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        intended(allOf(toPackage("com.footballmania"), hasComponent(MainActivity.class.getName())));

        Thread.sleep(2000);

        onView(withId(R.id.articlesTransfers))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(0, click())
                );

        Thread.sleep(3000);
    }
}
