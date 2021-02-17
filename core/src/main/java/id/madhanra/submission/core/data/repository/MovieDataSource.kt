package id.madhanra.submission.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.madhanra.submission.core.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.core.data.source.local.entity.MoviesEntity
import id.madhanra.submission.core.vo.Resource

interface MovieDataSource {
    fun getAllMovies(sort: String): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getDetailMovie(id: Int): LiveData<Resource<DetailMovieEntity>>

    fun getAMovie(id: Int): LiveData<MoviesEntity>

    fun getFavoredMovies(): LiveData<PagedList<MoviesEntity>>

    fun setFavorite(movie: MoviesEntity, favorite: Boolean, detailMovie: DetailMovieEntity)
}