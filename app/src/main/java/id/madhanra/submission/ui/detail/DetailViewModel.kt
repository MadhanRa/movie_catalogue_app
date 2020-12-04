package id.madhanra.submission.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.repository.MovieRepository
import id.madhanra.submission.data.source.repository.TvShowRepository
import id.madhanra.submission.vo.Resource

class DetailViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository,
    ): ViewModel() {

    private lateinit var id : String

    fun setSelectedItem(id: String) {
        this.id = id
    }

    fun getDetailMovie(): LiveData<Resource<DetailMovieEntity>> = movieRepository.getDetailMovie(id.toInt())


    fun getDetailTvShow(): LiveData<Resource<DetailTvShowEntity>> = tvShowRepository.getDetailTvShow(id.toInt())

    fun getAMovie(): LiveData<MoviesEntity> = movieRepository.getAMovie(id.toInt())

    fun getATvShow(): LiveData<TvShowEntity> = tvShowRepository.getATvShow(id.toInt())

    fun setFavoriteMovie(movieEntity: DetailMovieEntity, aMovieEntity: MoviesEntity) {
        val isFavorite = !movieEntity.favorite
        movieRepository.setFavorite(aMovieEntity, isFavorite, movieEntity)
    }

    fun setFavoriteTvShow(tvShowEntity: DetailTvShowEntity, aTvShowEntity: TvShowEntity) {
        val isFavorite = !tvShowEntity.favorite
        tvShowRepository.setFavorite(aTvShowEntity, isFavorite, tvShowEntity)
    }

}