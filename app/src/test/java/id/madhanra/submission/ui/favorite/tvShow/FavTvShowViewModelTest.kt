package id.madhanra.submission.ui.favorite.tvShow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import id.madhanra.submission.core.data.source.local.entity.TvShowEntity
import id.madhanra.submission.core.data.repository.TvShowRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavTvShowViewModelTest {
    private lateinit var viewModel: FavTvShowViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var  tvShowRepository: TvShowRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var  pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = FavTvShowViewModel(tvShowRepository)
    }

    @Test
    fun getFavTvShows() {
        val dummyTvShows = pagedList
        Mockito.`when`(dummyTvShows.size).thenReturn(20)
        val movies = MutableLiveData<PagedList<TvShowEntity>>()
        movies.value = dummyTvShows

        Mockito.`when`(tvShowRepository.getFavoredTvShows()).thenReturn(movies)
        val movieEntities = viewModel.getFavTvShows().value
        verify(tvShowRepository).getFavoredTvShows()
        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.size)

        viewModel.getFavTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}