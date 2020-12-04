package id.madhanra.submission.ui.favorite.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.repository.MovieRepository

class FavMovieViewModel @ViewModelInject constructor(private val repository: MovieRepository): ViewModel(){
    fun getFavMovies() : LiveData<PagedList<MoviesEntity>> = repository.getFavoredMovies()
}