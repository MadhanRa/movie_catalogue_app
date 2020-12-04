package id.madhanra.submission.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.data.source.repository.MovieRepository
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.vo.Resource


class MovieViewModel @ViewModelInject constructor(private val repository: MovieRepository) : ViewModel() {
    fun getMovies(sort: String) : LiveData<Resource<PagedList<MoviesEntity>>> = repository.getAllMovies(sort)

}