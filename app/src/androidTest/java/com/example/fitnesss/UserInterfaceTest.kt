package com.example.fitnesss

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class UserInterfaceTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldCalculateData() {
        onView(withId(R.id.calculatorFragment)).perform(click())
        onView(withId(R.id.edit_age)).perform(ViewActions.typeText("23"))
        onView(withId(R.id.edit_weight)).perform(ViewActions.typeText("54"))
        onView(withId(R.id.edit_height)).perform(ViewActions.typeText("176"))
        onView(withId(R.id.edit_gender)).perform(click())
        onView(withText("Женский"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())
        onView(withId(R.id.button)).perform(click())
        Thread.sleep(2000)
        onView(withText("Индекс массы тела:")).check(matches(isDisplayed()))
    }
}

