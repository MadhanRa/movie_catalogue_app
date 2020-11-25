package id.madhanra.submission.ui.tvShow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.madhanra.submission.data.source.TvShowRepository
import id.madhanra.submission.data.source.local.entity.TvShowEntity

class TvShowViewModel @ViewModelInject constructor(private val repository: TvShowRepository) : ViewModel() {
    fun getTvShows() : LiveData<List<TvShowEntity>> = repository.getTvShow()
    fun getLoading() : LiveData<Boolean> = repository.isLoading

    override fun onCleared() {
        super.onCleared()
        repository.clearComposite()
    }
}