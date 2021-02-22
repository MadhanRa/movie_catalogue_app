package id.madhanra.submission.core.data.repository

import androidx.paging.PagedList
import id.madhanra.submission.core.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.core.data.source.local.entity.MoviesEntity
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable

interface MovieDataSource {
    fun getAllMovies(sort: String): Flowable<Resource<PagedList<MoviesEntity>>>

    fun getDetailMovie(id: Int): Flowable<Resource<DetailMovieEntity>>

    fun getAMovie(id: Int): Flowable<MoviesEntity>

    fun getFavoredMovies(): Flowable<PagedList<MoviesEntity>>

    fun setFavorite(movie: MoviesEntity, favorite: Boolean, detailMovie: DetailMovieEntity)
}