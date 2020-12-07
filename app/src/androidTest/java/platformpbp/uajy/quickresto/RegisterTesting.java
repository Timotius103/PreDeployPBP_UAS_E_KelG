package platformpbp.uajy.quickresto;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterTesting {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void registerTesting() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button7), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.page1),
                                        2),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.FullName_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Timotius"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("081246843543"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.pass),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pass_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("12345678"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.Next), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.mail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.mail_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("tiodong99@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.phone), withText("081246843543"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText(""));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.Next), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("081246843543"), closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.name), withText("Timotius"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.FullName_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(replaceText(""));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.FullName_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.Next), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.FullName_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("Timotius"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.Next), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        materialButton5.perform(click());
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
