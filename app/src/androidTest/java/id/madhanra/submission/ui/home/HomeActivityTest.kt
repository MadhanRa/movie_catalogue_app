package id.madhanra.submission.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click


import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.madhanra.submission.R
import id.madhanra.submission.utils.DataSource
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovie = DataSource.generateMovie()
    private val dummyTvShow = DataSource.generateTvShow()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.text_year)).perform(ViewActions.swipeUp()).check(matches(isDisplayed()))
        onView(withId(R.id.text_year)).check(matches(withText(dummyMovie[0].year)))
        onView(withId(R.id.text_length)).perform(ViewActions.swipeUp()).check(matches(isDisplayed()))
        onView(withId(R.id.text_length)).check(matches(withText(dummyMovie[0].length)))
        onView(withId(R.id.text_genre)).perform(ViewActions.swipeUp()).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(withText(dummyMovie[0].genre)))
        onView(withId(R.id.text_certification)).perform(ViewActions.swipeUp()).check(matches(isDisplayed()))
        onView(withId(R.id.text_certification)).check(matches(withText(dummyMovie[0].certification)))
        onView(withId(R.id.text_user_score)).perform(ViewActions.swipeUp())
        onView(withId(R.id.text_user_score)).perform(ViewActions.swipeUp())
        onView(withId(R.id.text_user_score)).perform(ViewActions.swipeUp())
        onView(withId(R.id.text_user_score)).perform(ViewActions.swipeUp())
        onView(withId(R.id.text_user_score)).perform(ViewActions.swipeUp()).check(matches(isDisplayed()))
        onView(withId(R.id.text_user_score)).check(matches(withText(dummyMovie[0].userScore)))
        onView(withId(R.id.text_tagline)).perform(ViewActions.swipeUp()).check(matches(isDisplayed()))
        onView(withId(R.id.text_tagline)).check(matches(withText(dummyMovie[0].tagline)))
        onView(withId(R.id.text_overview)).perform(ViewActions.swipeUp()).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).check(matches(withText(dummyMovie[0].overview)))
    }

    @Test
    fun loadTvShow() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

}