package id.madhanra.submission.ui.detail

import id.madhanra.submission.utils.DataSource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DetailViewModelTest {

    private lateinit var viewModel1: DetailViewModel
    private lateinit var viewModel2: DetailViewModel
    private val dummyMovie = DataSource.generateMovie()[0]
    private val dummyTvShow = DataSource.generateTvShow()[0]
    private val movieId = dummyMovie.id
    private val tvShowId = dummyTvShow.id

    @Before
    fun setUp() {
        viewModel1 = DetailViewModel()
        viewModel1.setSelectedItem(movieId)
        viewModel2 = DetailViewModel()
        viewModel2.setSelectedItem(tvShowId)
    }

    @Test
    fun getMovie() {
        viewModel1.setSelectedItem(dummyMovie.id)
        val movieEntity = viewModel1.getMovie()
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.overview, movieEntity.overview)
        assertEquals(dummyMovie.year, movieEntity.year)
        assertEquals(dummyMovie.length, movieEntity.length)
        assertEquals(dummyMovie.genre, movieEntity.genre)
        assertEquals(dummyMovie.tagline, movieEntity.tagline)
        assertEquals(dummyMovie.certification, movieEntity.certification)
        assertEquals(dummyMovie.userScore, movieEntity.userScore)
        assertEquals(dummyMovie.imagePath, movieEntity.imagePath)
    }

    @Test
    fun getTvShow() {
        viewModel2.setSelectedItem(dummyTvShow.id)
        val tvShowEntity = viewModel2.getTvShow()
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.id, tvShowEntity.id)
        assertEquals(dummyTvShow.title, tvShowEntity.title)
        assertEquals(dummyTvShow.overview, tvShowEntity.overview)
        assertEquals(dummyTvShow.year, tvShowEntity.year)
        assertEquals(dummyTvShow.length, tvShowEntity.length)
        assertEquals(dummyTvShow.genre, tvShowEntity.genre)
        assertEquals(dummyTvShow.tagline, tvShowEntity.tagline)
        assertEquals(dummyTvShow.certification, tvShowEntity.certification)
        assertEquals(dummyTvShow.userScore, tvShowEntity.userScore)
        assertEquals(dummyTvShow.imagePath, tvShowEntity.imagePath)
    }
}