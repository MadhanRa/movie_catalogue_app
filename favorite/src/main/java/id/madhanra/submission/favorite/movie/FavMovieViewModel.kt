package id.madhanra.submission.favorite.movie

import androidx.lifecycle.*
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.domain.usecase.MoviesUseCase


class FavMovieViewModel (private val movieUseCase: MoviesUseCase): ViewModel(){
    private val refreshTrigger = MutableLiveData(Unit)

    private var movieList = refreshTrigger.switchMap {
        movieUseCase.getFavoredMovies().asLiveData()
    }

    fun getFavMovies() : LiveData<Resource<List<Show>>> = movieList

    fun refresh(){
        refreshTrigger.value = Unit
    }
}