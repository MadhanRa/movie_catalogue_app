package id.madhanra.submission.ui.movie

import androidx.lifecycle.*
import id.madhanra.submission.core.domain.usecase.MoviesUseCase
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show

class MovieViewModel(movieUseCase: MoviesUseCase) : ViewModel() {
    // If already shimmering or not
    private var isAlreadyShimmer: Boolean = false

    // Used for Load More
    private val page = MutableLiveData<Int>()

    // Get Movie, triggered when page is set by setPage() or if refresh() is called
    private var popularMovieList = page.switchMap {
        movieUseCase.getPopularMovies(it).asLiveData()
    }

    fun setAlreadyShimmer() {
        isAlreadyShimmer = true
    }

    fun setPage(page: Int){
        this.page.postValue(page)
    }

    fun getIsAlreadyShimmering() = isAlreadyShimmer

    fun getPopularMovies() : LiveData<Resource<List<Show>>> = popularMovieList

    fun refresh() {
        page.postValue(page.value)
    }


}