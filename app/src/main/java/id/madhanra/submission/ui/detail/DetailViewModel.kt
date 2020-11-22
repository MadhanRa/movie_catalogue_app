package id.madhanra.submission.ui.detail

import androidx.lifecycle.ViewModel
import id.madhanra.submission.data.DataEntity
import id.madhanra.submission.utils.DataSource

class DetailViewModel : ViewModel() {
    private lateinit var id : String

    fun setSelectedItem(id: String) {
        this.id = id
    }

    fun getMovie(): DataEntity {
        lateinit var movie: DataEntity
        val moviesEntities = DataSource.generateMovie()
        for (movieEntity in moviesEntities) {
            if (movieEntity.id == id) {
                movie = movieEntity
            }
        }
        return movie
    }

    fun getTvShow(): DataEntity {
        lateinit var tvShow: DataEntity
        val tvShowsEntities = DataSource.generateTvShow()
        for (tvShowEntity in tvShowsEntities) {
            if (tvShowEntity.id == id) {
                tvShow = tvShowEntity
            }
        }
        return tvShow
    }
}