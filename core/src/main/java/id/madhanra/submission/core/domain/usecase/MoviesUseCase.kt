package id.madhanra.submission.core.domain.usecase

import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.DetailMovies
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable

interface MoviesUseCase {
    fun getAllMovies(page: Int, sort: String): Flowable<Resource<PagedList<Movies>>>

    fun getDetailMovie(id: Int): Flowable<Resource<DetailMovies>>

    fun getAMovie(id: Int): Flowable<Movies>

    fun getFavoredMovies(): Flowable<List<Movies>>

    fun setFavorite(movie: Movies, favorite: Boolean, detailMovie: DetailMovies)
}