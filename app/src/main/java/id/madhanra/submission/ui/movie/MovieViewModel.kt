package id.madhanra.submission.ui.movie

import androidx.lifecycle.ViewModel
import id.madhanra.submission.data.DataEntity
import id.madhanra.submission.utils.DataSource


class MovieViewModel : ViewModel() {
    fun getMovies() : List<DataEntity> = DataSource.generateMovie()
}