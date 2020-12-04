package id.madhanra.submission.ui.favorite.tvShow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.repository.TvShowRepository

class FavTvShowViewModel @ViewModelInject constructor(private val repository: TvShowRepository):
    ViewModel() {
    fun getFavTvShows(): LiveData<PagedList<TvShowEntity>> = repository.getFavoredTvShows()
}