package id.madhanra.submission.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.madhanra.submission.core.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.core.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.core.data.repository.MovieRepository
import id.madhanra.submission.core.data.repository.TvShowRepository
import id.madhanra.submission.core.utils.DataDummy
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
class DetailViewModelTest {

    private lateinit var viewModel1: DetailViewModel
    private lateinit var viewModel2: DetailViewModel
    private val dummyMovie = Resource.success(DataDummy.generateMovieDetail())
    private val dummyTvShow = Resource.success(DataDummy.generateTvShowDetail())
    private val movieId = dummyMovie.data?.id
    private val tvShowId = dummyTvShow.data?.id

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<DetailMovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<DetailTvShowEntity>>

    @Before
    fun setUp() {
        viewModel1 = DetailViewModel(movieRepository, tvShowRepository)
        viewModel1.setSelectedItem(movieId.toString())
        viewModel2 = DetailViewModel(movieRepository, tvShowRepository)
        viewModel2.setSelectedItem(tvShowId.toString())
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<Resource<DetailMovieEntity>>()
        movie.value = dummyMovie

        `when`(movieId?.let { movieRepository.getDetailMovie(it) }).thenReturn(movie)
        val movieEntity = viewModel1.getDetailMovie().value
        if (movieId != null) {
            verify(movieRepository).getDetailMovie(movieId)
        }
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.data?.id, movieEntity?.data?.id)
        assertEquals(dummyMovie.data?.title, movieEntity?.data?.title)
        assertEquals(dummyMovie.data?.overview, movieEntity?.data?.overview)
        assertEquals(dummyMovie.data?.releaseDate, movieEntity?.data?.releaseDate)
        assertEquals(dummyMovie.data?.runtime, movieEntity?.data?.runtime)
        assertEquals(dummyMovie.data?.genres, movieEntity?.data?.genres)
        assertEquals(dummyMovie.data?.tagLine, movieEntity?.data?.tagLine)
        assertEquals(dummyMovie.data?.voteAverage, movieEntity?.data?.voteAverage)
        assertEquals(dummyMovie.data?.posterPath, movieEntity?.data?.posterPath)

        viewModel1.getDetailMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<Resource<DetailTvShowEntity>>()
        tvShow.value = dummyTvShow

        `when`(tvShowId?.let { tvShowRepository.getDetailTvShow(it) }).thenReturn(tvShow)
        val tvShowEntity = viewModel2.getDetailTvShow().value
        if (tvShowId != null) {
            verify(tvShowRepository).getDetailTvShow(tvShowId)
        }
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.data?.id, tvShowEntity?.data?.id)
        assertEquals(dummyTvShow.data?.name, tvShowEntity?.data?.name)
        assertEquals(dummyTvShow.data?.overview, tvShowEntity?.data?.overview)
        assertEquals(dummyTvShow.data?.firstAirDate, tvShowEntity?.data?.firstAirDate)
        assertEquals(dummyTvShow.data?.episodeRunTime, tvShowEntity?.data?.episodeRunTime)
        assertEquals(dummyTvShow.data?.genres, tvShowEntity?.data?.genres)
        assertEquals(dummyTvShow.data?.tagLine, tvShowEntity?.data?.tagLine)
        assertEquals(dummyTvShow.data?.voteAverage, tvShowEntity?.data?.voteAverage)
        assertEquals(dummyTvShow.data?.posterPath, tvShowEntity?.data?.posterPath)

        viewModel2.getDetailTvShow().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }
}