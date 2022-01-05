package com.sm.sdt.firebasekeyloggerandroird.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sm.sdt.firebasekeyloggerandroird.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class parentLoginTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun parentLoginTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(500)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.parent_email),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.parent_email),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("shahadat@gmail.com"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.parent_password),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard())



        val materialButton = onView(
            allOf(
                withId(R.id.paretn_signin_or_register_btn), withText("Parent Register Or Sign In"),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val materialButton2 = onView(
            allOf(
                withId(R.id.logout), withText("Logout"),
                childAtPosition(
                    allOf(
                        withId(R.id.child_list_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.parent_email),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("samiya@gmail.com"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.parent_password),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("123456"), closeSoftKeyboard())

        val materialButton5 = onView(
            allOf(
                withId(R.id.paretn_signin_or_register_btn), withText("Parent Register Or Sign In"),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val materialButton3 = onView(
            allOf(
                withId(R.id.logout), withText("Logout"),
                childAtPosition(
                    allOf(
                        withId(R.id.child_list_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
