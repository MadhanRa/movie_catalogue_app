package id.madhanra.submission.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import id.madhanra.submission.core.data.source.local.entity.MoviesEntity
import id.madhanra.submission.core.data.repository.MovieRepository
import id.madhanra.submission.core.utils.SortUtils
import id.madhanra.submission.core.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MoviesEntity>>>

    @Mock
    private lateinit var  pagedList: PagedList<MoviesEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(20)
        val movies = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies(SortUtils.DEFAULT)).thenReturn(movies)
        val movieEntities = viewModel.getMovies(SortUtils.DEFAULT).value?.data
        verify(movieRepository).getAllMovies(SortUtils.DEFAULT)
        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.size)

        viewModel.getMovies(SortUtils.DEFAULT).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}