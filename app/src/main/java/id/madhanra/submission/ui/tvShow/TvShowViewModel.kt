package id.madhanra.submission.ui.tvShow

import androidx.lifecycle.ViewModel
import id.madhanra.submission.data.DataEntity
import id.madhanra.submission.utils.DataSource

class TvShowViewModel : ViewModel() {
    fun getTvShows() : List<DataEntity> = DataSource.generateTvShow()
}