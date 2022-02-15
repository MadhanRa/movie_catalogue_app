package id.madhanra.submission.favorite.tvShow

import androidx.lifecycle.*
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase

class FavTvShowViewModel (private val tvShowUseCase: TvShowsUseCase):
    ViewModel() {
    private val refreshTrigger = MutableLiveData(Unit)

    private var tvShowList = refreshTrigger.switchMap {
        tvShowUseCase.getFavoredTvShows().asLiveData()
    }

    fun getFavTvShows(): LiveData<Resource<List<Show>>> = tvShowList

    fun refresh() {
        refreshTrigger.value = Unit
    }
}