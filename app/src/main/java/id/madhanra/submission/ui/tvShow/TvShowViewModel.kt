package id.madhanra.submission.ui.tvShow

import androidx.lifecycle.*
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show

class TvShowViewModel (private val tvShowUseCase: TvShowsUseCase) : ViewModel() {
    // If already shimmering or not
    private var isAlreadyShimmer: Boolean = false

    // Use for Load More
    private val page = MutableLiveData<Int>()

    private var tvShowList = page.switchMap {
        tvShowUseCase.getAllTvShows(it).asLiveData()
    }

    fun setAlreadyShimmer() {
        isAlreadyShimmer = true
    }

    fun setPage(page: Int){
        this.page.postValue(page)
    }

    fun getIsAlreadyShimmering() = isAlreadyShimmer

    fun getTvShows() : LiveData<Resource<List<Show>>> = tvShowList

    fun refresh() {
        page.postValue(page.value)
    }


}