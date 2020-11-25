package id.madhanra.submission.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.madhanra.submission.data.source.MovieRepository
import id.madhanra.submission.data.source.local.entity.MoviesEntity


class MovieViewModel @ViewModelInject constructor(private val repository: MovieRepository) : ViewModel() {
    fun getMovies() : LiveData<List<MoviesEntity>> = repository.getMovies()
    fun getLoading() : LiveData<Boolean> = repository.isLoading

    override fun onCleared() {
        super.onCleared()
        repository.clearComposite()
    }
}