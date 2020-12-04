package id.madhanra.submission.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.any
import id.madhanra.submission.data.source.local.TvShowLocalDataSource
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.remote.TvShowRemoteDataSource
import id.madhanra.submission.data.source.repository.TvShowRepository
import id.madhanra.submission.utils.*
import id.madhanra.submission.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
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

    private val remote = mock(TvShowRemoteDataSource::class.java)
    private val local = mock(TvShowLocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    @Suppress("UNCHECKED_CAST")
    private val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>

    private val dummyRemoteTvShows = DataDummy.generateRemoteTvShow()
    private val dummyRemoteTvShow = DataDummy.generateRemoteTvShowDetail()

    @Before
    fun setUp() {
        tvShowRepository = TvShowRepository(remote, local, appExecutors)
    }

    @Test
    fun getTvShow() {
        `when`(local.getAllTvShow()).thenReturn(dataSourceFactory)
        tvShowRepository.getTvShow()

        val tvShowsResult = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateTvShow()))
        verify(local).getAllTvShow()
        assertNotNull(tvShowsResult.data)
        assertEquals(dummyRemoteTvShows.results.size, tvShowsResult.data?.size)
    }

    @Test
    fun getFavoredTvShows(){
        `when`(local.getFavoredTvShows()).thenReturn(dataSourceFactory)
        tvShowRepository.getFavoredTvShows()

        val tvShowsResult = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateTvShow()))
        verify(local).getFavoredTvShows()
        assertNotNull(tvShowsResult.data)
        assertEquals(dummyRemoteTvShows.results.size, tvShowsResult.data?.size)
    }

    @Test
    fun getDetailTvShow() {
        val dummyTvShow = MutableLiveData<DetailTvShowEntity>()
        dummyTvShow.value = DataDummy.generateTvShowDetail()
        `when`(local.getDetailTvShow(any())).thenReturn(dummyTvShow)

        val tvShowResult = LiveDataTestUtil.getValue(tvShowRepository.getDetailTvShow(any()))
        verify(local).getDetailTvShow(any())
        assertNotNull(tvShowResult)
        assertNotNull(tvShowResult.data?.name)
        assertEquals(dummyRemoteTvShow.name, tvShowResult.data?.name)
        assertNotNull(tvShowResult.data?.episodeRunTime)
        assertEquals(dummyRemoteTvShow.episodeRunTime, tvShowResult.data?.episodeRunTime)
        assertNotNull(tvShowResult.data?.firstAirDate)
        assertEquals(dummyRemoteTvShow.firstAirDate, tvShowResult.data?.firstAirDate)
        assertNotNull(tvShowResult.data?.genres)
        assertEquals(dummyRemoteTvShow.genres, tvShowResult.data?.genres)
        assertNotNull(tvShowResult.data?.id)
        assertEquals(dummyRemoteTvShow.id, tvShowResult.data?.id)
        assertNotNull(tvShowResult.data?.overview)
        assertEquals(dummyRemoteTvShow.overview, tvShowResult.data?.overview)
        assertNotNull(tvShowResult.data?.posterPath)
        assertEquals(dummyRemoteTvShow.posterPath, tvShowResult.data?.posterPath)
        assertNotNull(tvShowResult.data?.tagLine)
        assertEquals(dummyRemoteTvShow.tagLine, tvShowResult.data?.tagLine)
        assertNotNull(tvShowResult.data?.voteAverage)
        assertEquals(dummyRemoteTvShow.voteAverage, tvShowResult.data?.voteAverage)

    }
}