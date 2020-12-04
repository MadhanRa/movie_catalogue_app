package id.madhanra.submission.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import id.madhanra.submission.data.source.local.MovieLocalDataSource
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.remote.MovieRemoteDataSource
import id.madhanra.submission.data.source.repository.MovieRepository
import id.madhanra.submission.utils.*
import id.madhanra.submission.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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

    private val remote = mock(MovieRemoteDataSource::class.java)
    private val local = mock(MovieLocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    @Suppress("UNCHECKED_CAST")
    private val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>

    private val dummyRemoteMovies = DataDummy.generateRemoteMovies()
    private val dummyRemoteMovieDetail = DataDummy.generateRemoteMovieDetail()

    @Before
    fun setUp() {
        movieRepository = MovieRepository(remote, local, appExecutors)
    }


    @Test
    fun getMovies() {
        `when`(local.getAllMovies(SortUtils.DEFAULT)).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies(SortUtils.DEFAULT)

        val movieResult = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateMovies()))
        verify(local).getAllMovies(SortUtils.DEFAULT)
        assertNotNull(movieResult.data)
        assertEquals(dummyRemoteMovies.results.size.toLong(), movieResult.data?.size?.toLong())
    }

    @Test
    fun getFavoredMovies(){
        `when`(local.getFavoredMovies()).thenReturn(dataSourceFactory)
        movieRepository.getFavoredMovies()

        val movieResult = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateMovies()))
        verify(local).getFavoredMovies()
        assertNotNull(movieResult.data)
        assertEquals(dummyRemoteMovies.results.size.toLong(), movieResult.data?.size?.toLong())
    }

    @Test
    fun getDetailMovies() {
        val dummyMovie = MutableLiveData<DetailMovieEntity>()
        dummyMovie.value = DataDummy.generateMovieDetail()
        `when`(local.getDetailMovie(any())).thenReturn(dummyMovie)

        val movieEntity = LiveDataTestUtil.getValue(movieRepository.getDetailMovie(any()))
        verify(local).getDetailMovie(any())
        assertNotNull(movieEntity)
        assertNotNull(movieEntity.data?.title)
        assertEquals(dummyRemoteMovieDetail.title, movieEntity.data?.title)
        assertNotNull(movieEntity.data?.genres)
        assertEquals(dummyRemoteMovieDetail.genres, movieEntity.data?.genres)
        assertNotNull(movieEntity.data?.posterPath)
        assertEquals(dummyRemoteMovieDetail.posterPath, movieEntity.data?.posterPath)
        assertNotNull(movieEntity.data?.voteAverage)
        assertEquals(dummyRemoteMovieDetail.voteAverage, movieEntity.data?.voteAverage)
        assertNotNull(movieEntity.data?.tagLine)
        assertEquals(dummyRemoteMovieDetail.tagLine, movieEntity.data?.tagLine)
        assertNotNull(movieEntity.data?.runtime)
        assertEquals(dummyRemoteMovieDetail.runtime, movieEntity.data?.runtime)
        assertNotNull(movieEntity.data?.releaseDate)
        assertEquals(dummyRemoteMovieDetail.releaseDate, movieEntity.data?.releaseDate)
        assertNotNull(movieEntity.data?.id)
        assertEquals(dummyRemoteMovieDetail.id, movieEntity.data?.id)
        assertNotNull(movieEntity.data?.overview)
        assertEquals(dummyRemoteMovieDetail.overview, movieEntity.data?.overview)
    }
}

