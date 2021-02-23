package id.madhanra.submission.ui.movie

import androidx.lifecycle.*
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.model.PageSort
import id.madhanra.submission.core.domain.usecase.MoviesUseCase
import id.madhanra.submission.core.vo.Resource

class MovieViewModel(private val movieUseCase: MoviesUseCase) : ViewModel() {
    private val page = MutableLiveData<PageSort>()

    private var movieList = page.switchMap {
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getAllMovies(it.page, it.sort))
    }

    fun setPage(page: Int, sort: String){
        this.page.postValue(PageSort(page, sort))
    }
    fun getMovies() : LiveData<Resource<PagedList<Movies>>> = movieList

    fun refresh() {
        page.postValue(page.value)
    }


}