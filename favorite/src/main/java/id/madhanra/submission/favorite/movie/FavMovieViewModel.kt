package id.madhanra.submission.favorite.movie

import androidx.lifecycle.*
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.usecase.MoviesUseCase


class FavMovieViewModel (private val movieUseCase: MoviesUseCase): ViewModel(){
    private val refreshTrigger = MutableLiveData(Unit)

    private var movieList = refreshTrigger.switchMap {
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getFavoredMovies())
    }

    fun getFavMovies() : LiveData<List<Movies>> = movieList

    fun refresh(){
        refreshTrigger.value = Unit
    }
}