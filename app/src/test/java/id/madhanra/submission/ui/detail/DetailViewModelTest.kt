package id.madhanra.submission.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.madhanra.submission.data.source.MovieRepository
import id.madhanra.submission.data.source.TvShowRepository
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel1: DetailViewModel
    private lateinit var viewModel2: DetailViewModel
    private val dummyMovie = DataDummy.generateMovieDetail()
    private val dummyTvShow = DataDummy.generateTvShowDetail()
    private val movieId = dummyMovie.id
    private val tvShowId = dummyTvShow.id

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Mock
    private lateinit var movieObserver: Observer<DetailMovieEntity>

    @Mock
    private lateinit var tvShowObserver: Observer<DetailTvShowEntity>

    @Before
    fun setUp() {
        viewModel1 = DetailViewModel(movieRepository, tvShowRepository)
        viewModel1.setSelectedItem(movieId.toString())
        viewModel2 = DetailViewModel(movieRepository, tvShowRepository)
        viewModel2.setSelectedItem(tvShowId.toString())
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<DetailMovieEntity>()
        movie.value = dummyMovie

        `when`(movieId?.let { movieRepository.getDetailMovie(it) }).thenReturn(movie)
        val movieEntity = viewModel1.getDetailMovie().value as DetailMovieEntity
        if (movieId != null) {
            verify(movieRepository).getDetailMovie(movieId)
        }
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.overview, movieEntity.overview)
        assertEquals(dummyMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dummyMovie.runtime, movieEntity.runtime)
        assertEquals(dummyMovie.genres, movieEntity.genres)
        assertEquals(dummyMovie.tagLine, movieEntity.tagLine)
        assertEquals(dummyMovie.voteAverage, movieEntity.voteAverage)
        assertEquals(dummyMovie.posterPath, movieEntity.posterPath)

        viewModel1.getDetailMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<DetailTvShowEntity>()
        tvShow.value = dummyTvShow

        `when`(tvShowId?.let { tvShowRepository.getDetailTvShow(it) }).thenReturn(tvShow)
        val tvShowEntity = viewModel2.getDetailTvShow().value as DetailTvShowEntity
        if (tvShowId != null) {
            verify(tvShowRepository).getDetailTvShow(tvShowId)
        }
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.id, tvShowEntity.id)
        assertEquals(dummyTvShow.name, tvShowEntity.name)
        assertEquals(dummyTvShow.overview, tvShowEntity.overview)
        assertEquals(dummyTvShow.firstAirDate, tvShowEntity.firstAirDate)
        assertEquals(dummyTvShow.episodeRunTime, tvShowEntity.episodeRunTime)
        assertEquals(dummyTvShow.genres, tvShowEntity.genres)
        assertEquals(dummyTvShow.tagLine, tvShowEntity.tagLine)
        assertEquals(dummyTvShow.voteAverage, tvShowEntity.voteAverage)
        assertEquals(dummyTvShow.posterPath, tvShowEntity.posterPath)

        viewModel2.getDetailTvShow().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }
}