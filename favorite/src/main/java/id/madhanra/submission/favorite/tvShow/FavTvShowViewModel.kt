package id.madhanra.submission.favorite.tvShow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase

class FavTvShowViewModel (private val tvShowUseCase: TvShowsUseCase):
    ViewModel() {
    fun getFavTvShows(): LiveData<PagedList<TvShows>> = LiveDataReactiveStreams.fromPublisher(tvShowUseCase.getFavoredTvShows())
}