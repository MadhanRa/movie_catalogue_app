package id.madhanra.submission.favorite.tvShow

import androidx.lifecycle.*
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase

class FavTvShowViewModel (private val tvShowUseCase: TvShowsUseCase):
    ViewModel() {
    private val refreshTrigger = MutableLiveData(Unit)

    private var tvShowList = refreshTrigger.switchMap {
        LiveDataReactiveStreams.fromPublisher(tvShowUseCase.getFavoredTvShows())
    }

    fun getFavTvShows(): LiveData<List<TvShows>> = tvShowList

    fun refresh() {
        refreshTrigger.value = Unit
    }
}