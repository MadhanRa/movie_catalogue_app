package id.madhanra.submission.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.madhanra.submission.core.domain.model.DetailMovies
import id.madhanra.submission.core.domain.model.DetailTvShows
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.usecase.MoviesUseCase
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase
import id.madhanra.submission.core.vo.Resource

class DetailViewModel(
    private val movieUseCase: MoviesUseCase,
    private val tvShowUseCase: TvShowsUseCase,
    ): ViewModel() {

    private lateinit var id : String

    fun setSelectedItem(id: String) {
        this.id = id
    }

    fun getDetailMovie(): LiveData<Resource<DetailMovies>> = LiveDataReactiveStreams.fromPublisher(movieUseCase.getDetailMovie(id.toInt()))


    fun getDetailTvShow(): LiveData<Resource<DetailTvShows>> = LiveDataReactiveStreams.fromPublisher(tvShowUseCase.getDetailTvShow(id.toInt()))

    fun getAMovie(): LiveData<Movies> = LiveDataReactiveStreams.fromPublisher(movieUseCase.getAMovie(id.toInt()))

    fun getATvShow(): LiveData<TvShows> = LiveDataReactiveStreams.fromPublisher(tvShowUseCase.getATvShow(id.toInt()))

    fun setFavoriteMovie(movieEntity: DetailMovies, aMovieEntity: Movies) {
        val isFavorite = !movieEntity.favorite
        movieUseCase.setFavorite(aMovieEntity, isFavorite, movieEntity)
    }

    fun setFavoriteTvShow(tvShowEntity: DetailTvShows, aTvShowEntity: TvShows) {
        val isFavorite = !tvShowEntity.favorite
        tvShowUseCase.setFavorite(aTvShowEntity, isFavorite, tvShowEntity)
    }

}