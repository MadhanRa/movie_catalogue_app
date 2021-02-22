package id.madhanra.submission.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.usecase.MoviesUseCase


class FavMovieViewModel (private val movieUseCase: MoviesUseCase): ViewModel(){
    fun getFavMovies() : LiveData<PagedList<Movies>> = LiveDataReactiveStreams.fromPublisher(movieUseCase.getFavoredMovies())
}