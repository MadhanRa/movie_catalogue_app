package id.madhanra.submission.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.repository.MovieRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavMovieViewModelTest {
    private lateinit var viewModel: FavMovieViewModel

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var  movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MoviesEntity>>

    @Mock
    private lateinit var  pagedList: PagedList<MoviesEntity>

    @Before
    fun setUp() {
        viewModel = FavMovieViewModel(movieRepository)
    }

    @Test
    fun getFavMovies() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(20)
        val movies = MutableLiveData<PagedList<MoviesEntity>>()
        movies.value = dummyMovies

        `when`(movieRepository.getFavoredMovies()).thenReturn(movies)
        val movieEntities = viewModel.getFavMovies().value
        verify(movieRepository).getFavoredMovies()
        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.size)

        viewModel.getFavMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}