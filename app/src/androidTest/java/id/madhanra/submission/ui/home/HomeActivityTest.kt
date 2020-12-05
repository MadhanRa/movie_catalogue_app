package id.madhanra.submission.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.madhanra.submission.R
import id.madhanra.submission.utils.DataDummy
import id.madhanra.submission.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovies = DataDummy.generateMovies()
    private val dummyTvShow = DataDummy.generateTvShow()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_length)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_user_score)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_tagline)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadTvShow() {
        onView(withContentDescription(R.string.tv_show)).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withContentDescription(R.string.tv_show)).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_length)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_user_score)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_tagline)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadMoviesFav() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.favorite_button)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_button)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withContentDescription(R.string.favorite)).perform(click())
        onView(withId(R.id.rv_fav_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_length)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_user_score)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_tagline)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShowFav() {
        onView(withContentDescription(R.string.tv_show)).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.favorite_button)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_button)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withContentDescription(R.string.favorite)).perform(click())
        onView(withContentDescription(R.string.tv_show_fav)).perform(click())
        onView(withId(R.id.rv_fav_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_length)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_user_score)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_tagline)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).perform(NestedScrollViewExtension()).check(matches(isDisplayed()))
    }



}