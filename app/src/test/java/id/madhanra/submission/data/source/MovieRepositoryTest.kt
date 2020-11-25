package id.madhanra.submission.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import id.madhanra.submission.data.source.remote.ApiService
import id.madhanra.submission.data.source.remote.response.MovieResponse
import id.madhanra.submission.data.source.remote.response.MoviesItem
import id.madhanra.submission.utils.DataDummy
import id.madhanra.submission.utils.LiveDataTestUtil
import id.madhanra.submission.utils.RxImmediateSchedulerRule
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert.*
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock



class MovieRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
    lateinit var movieRepository: MovieRepository
    private val movieApi = mock(ApiService::class.java)
    private val compositeDisposable = mock(CompositeDisposable::class.java)

    private val dummyMovies = DataDummy.generateRemoteMovies()
    private val dummyMovie = DataDummy.generateRemoteMovieDetail()

    @Before
    fun setUp() {
        movieRepository = MovieRepository(movieApi, compositeDisposable)
    }

    @Test
    fun getMovies() {
        `when`(movieApi.getPopularMovies(any())).thenReturn(Single.just(dummyMovies))
        val movieResult = LiveDataTestUtil.getValue(movieRepository.getMovies())
        verify(movieApi).getPopularMovies(any())
        assertNotNull(movieResult)
        assertEquals(dummyMovies.results.size, movieResult.size)
    }

    @Test
    fun getDetailMovies() {
        `when`(movieApi.getMovieDetail(any())).thenReturn(Single.just(dummyMovie))
        val movieEntity = LiveDataTestUtil.getValue(movieRepository.getDetailMovie(any()))
        verify(movieApi).getMovieDetail(any())
        assertNotNull(movieEntity)
        assertNotNull(movieEntity.title)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertNotNull(movieEntity.genres)
        assertEquals(dummyMovie.genres, movieEntity.genres)
        assertNotNull(movieEntity.posterPath)
        assertEquals(dummyMovie.posterPath, movieEntity.posterPath)
        assertNotNull(movieEntity.voteAverage)
        assertEquals(dummyMovie.voteAverage, movieEntity.voteAverage)
        assertNotNull(movieEntity.tagLine)
        assertEquals(dummyMovie.tagLine, movieEntity.tagLine)
        assertNotNull(movieEntity.runtime)
        assertEquals(dummyMovie.runtime, movieEntity.runtime)
        assertNotNull(movieEntity.releaseDate)
        assertEquals(dummyMovie.releaseDate, movieEntity.releaseDate)
        assertNotNull(movieEntity.id)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertNotNull(movieEntity.overview)
        assertEquals(dummyMovie.overview, movieEntity.overview)
    }
}

