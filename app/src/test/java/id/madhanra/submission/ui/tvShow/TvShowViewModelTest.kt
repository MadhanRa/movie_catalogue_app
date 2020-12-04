package id.madhanra.submission.ui.tvShow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.repository.TvShowRepository
import id.madhanra.submission.utils.TvShowSortUtils
import id.madhanra.submission.vo.Resource
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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(tvShowRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(20)
        val tvShows = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShows.value = dummyTvShows

        `when`(tvShowRepository.getTvShow(TvShowSortUtils.DEFAULT)).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows(TvShowSortUtils.DEFAULT).value?.data
        verify(tvShowRepository).getTvShow(TvShowSortUtils.DEFAULT)
        assertNotNull(tvShowEntities)
        assertEquals(20, tvShowEntities?.size)

        viewModel.getTvShows(TvShowSortUtils.DEFAULT).observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}