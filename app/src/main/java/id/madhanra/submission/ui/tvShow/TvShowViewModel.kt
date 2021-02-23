package id.madhanra.submission.ui.tvShow

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase
import id.madhanra.submission.core.vo.Resource

class TvShowViewModel (private val tvShowUseCase: TvShowsUseCase) : ViewModel() {
    fun getTvShows(sort: String) : LiveData<Resource<PagedList<TvShows>>> = LiveDataReactiveStreams.fromPublisher(tvShowUseCase.getTvShow(sort))


}