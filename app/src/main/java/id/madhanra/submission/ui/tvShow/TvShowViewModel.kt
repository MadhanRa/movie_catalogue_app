package id.madhanra.submission.ui.tvShow

import androidx.lifecycle.*
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.PageSort
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase
import id.madhanra.submission.core.vo.Resource

class TvShowViewModel (private val tvShowUseCase: TvShowsUseCase) : ViewModel() {
    private val page = MutableLiveData<PageSort>()

    private var tvShowList = page.switchMap {
        LiveDataReactiveStreams.fromPublisher(tvShowUseCase.getTvShow(it.page, it.sort))
    }

    fun setPage(page: Int, sort: String) {
        this.page.postValue(PageSort(page, sort))
    }

    fun getTvShows() : LiveData<Resource<PagedList<TvShows>>> = tvShowList

    fun refresh() {
        page.postValue(page.value)
    }


}