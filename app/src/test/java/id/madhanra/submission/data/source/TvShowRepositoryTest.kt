package id.madhanra.submission.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.madhanra.submission.data.source.remote.ApiService
import id.madhanra.submission.utils.DataDummy
import id.madhanra.submission.utils.LiveDataTestUtil
import id.madhanra.submission.utils.RxImmediateSchedulerRule
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Test
import com.nhaarman.mockitokotlin2.any
import org.junit.Assert.*
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.mockito.Mockito.*

class TvShowRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
    lateinit var tvShowRepository: TvShowRepository
    private val tvShowApi = mock(ApiService::class.java)
    private val compositeDisposable = mock(CompositeDisposable::class.java)

    private val dummyTvShows = DataDummy.generateRemoteTvShow()
    private val dummyTvShow = DataDummy.generateRemoteTvShowDetail()

    @Before
    fun setUp() {
        tvShowRepository = TvShowRepository(tvShowApi, compositeDisposable)
    }

    @Test
    fun getTvShow() {
        `when`(tvShowApi.getPopularTvShow(any())).thenReturn(Single.just(dummyTvShows))
        val tvShowsResult = LiveDataTestUtil.getValue(tvShowRepository.getTvShow())
        verify(tvShowApi).getPopularTvShow(anyInt())
        assertNotNull(tvShowsResult)
        assertEquals(dummyTvShows.results.size, tvShowsResult.size)
    }

    @Test
    fun getDetailTvShow() {
        `when`(tvShowApi.getTvShowDetail(any())).thenReturn(Single.just(dummyTvShow))
        val tvShowResult = LiveDataTestUtil.getValue(tvShowRepository.getDetailTvShow(any()))
        verify(tvShowApi).getTvShowDetail(any())
        assertNotNull(tvShowResult)
        assertNotNull(tvShowResult.name)
        assertEquals(dummyTvShow.name, tvShowResult.name)
        assertNotNull(tvShowResult.episodeRunTime)
        assertEquals(dummyTvShow.episodeRunTime, tvShowResult.episodeRunTime)
        assertNotNull(tvShowResult.firstAirDate)
        assertEquals(dummyTvShow.firstAirDate, tvShowResult.firstAirDate)
        assertNotNull(tvShowResult.genres)
        assertEquals(dummyTvShow.genres, tvShowResult.genres)
        assertNotNull(tvShowResult.id)
        assertEquals(dummyTvShow.id, tvShowResult.id)
        assertNotNull(tvShowResult.overview)
        assertEquals(dummyTvShow.overview, tvShowResult.overview)
        assertNotNull(tvShowResult.posterPath)
        assertEquals(dummyTvShow.posterPath, tvShowResult.posterPath)
        assertNotNull(tvShowResult.tagLine)
        assertEquals(dummyTvShow.tagLine, tvShowResult.tagLine)
        assertNotNull(tvShowResult.voteAverage)
        assertEquals(dummyTvShow.voteAverage, tvShowResult.voteAverage)

    }
}