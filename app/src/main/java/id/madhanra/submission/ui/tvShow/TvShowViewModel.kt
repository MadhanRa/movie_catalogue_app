package id.madhanra.submission.ui.tvShow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.madhanra.submission.data.source.repository.TvShowRepository
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.vo.Resource

class TvShowViewModel @ViewModelInject constructor(private val repository: TvShowRepository) : ViewModel() {
    fun getTvShows() : LiveData<Resource<PagedList<TvShowEntity>>> = repository.getTvShow()


}