package id.madhanra.submission.core.domain.repository

import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.DetailMovies
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable

interface IMoviesRepository {
    fun getAllMovies(sort: String): Flowable<Resource<PagedList<Movies>>>

    fun getDetailMovie(id: Int): Flowable<Resource<DetailMovies>>

    fun getAMovie(id: Int): Flowable<Movies>

    fun getFavoredMovies(): Flowable<PagedList<Movies>>

    fun setFavorite(movie: Movies, favorite: Boolean, detailMovie: DetailMovies)
}