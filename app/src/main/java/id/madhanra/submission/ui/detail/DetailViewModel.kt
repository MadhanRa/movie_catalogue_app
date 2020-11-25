package id.madhanra.submission.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.madhanra.submission.data.source.MovieRepository
import id.madhanra.submission.data.source.TvShowRepository
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity

class DetailViewModel @ViewModelInject constructor(private val movieRepository: MovieRepository, private val tvShowRepository: TvShowRepository): ViewModel() {
    private lateinit var id : String

    fun setSelectedItem(id: String) {
        this.id = id
    }

    fun getDetailMovie(): LiveData<DetailMovieEntity> = movieRepository.getDetailMovie(id.toInt())
    fun getLoading() : LiveData<Boolean> = movieRepository.isLoading

    fun getDetailTvShow(): LiveData<DetailTvShowEntity> = tvShowRepository.getDetailTvShow(id.toInt())
    fun getLoadingTvShow() : LiveData<Boolean> = tvShowRepository.isLoading

    override fun onCleared() {
        super.onCleared()
        movieRepository.clearComposite()
        tvShowRepository.clearComposite()
    }
}