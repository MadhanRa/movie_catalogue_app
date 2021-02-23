package id.madhanra.submission.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.usecase.MoviesUseCase
import id.madhanra.submission.core.vo.Resource

class MovieViewModel(private val movieUseCase: MoviesUseCase) : ViewModel() {
    fun getMovies(sort: String) : LiveData<Resource<PagedList<Movies>>> = LiveDataReactiveStreams.fromPublisher(movieUseCase.getAllMovies(sort))


}