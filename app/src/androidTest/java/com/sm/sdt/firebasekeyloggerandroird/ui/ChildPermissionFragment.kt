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
class ChildPermissionFragment {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun childPermissionFragment() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(500)

        val materialTextView = onView(
            allOf(
                withId(R.id.child_register_tv), withText("Child Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.parent_registration_login_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.parent_email),
                childAtPosition(
                    allOf(
                        withId(R.id.child_registration_fragment),
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
        appCompatEditText.perform(replaceText("shahadat@gmail.com"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.parent_password),
                childAtPosition(
                    allOf(
                        withId(R.id.child_registration_fragment),
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
        appCompatEditText2.perform(replaceText("123456"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.child_name),
                childAtPosition(
                    allOf(
                        withId(R.id.child_registration_fragment),
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
        appCompatEditText3.perform(replaceText("shahadat"), closeSoftKeyboard())

        pressBack()

        val materialButton = onView(
            allOf(
                withId(R.id.child_register_btn), withText("Register Child"),
                childAtPosition(
                    allOf(
                        withId(R.id.child_registration_fragment),
                        childAtPosition(
                            withId(R.id.fragment_container_view),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
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
