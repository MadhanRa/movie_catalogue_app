package id.madhanra.submission.core.domain.usecase

import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.DetailMovies
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.repository.IMoviesRepository
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable
import javax.inject.Inject

class MoviesInteractor @Inject constructor(private val moviesRepository: IMoviesRepository): MoviesUseCase {
    override fun getAllMovies(sort: String): Flowable<Resource<PagedList<Movies>>> = moviesRepository.getAllMovies(sort)

    override fun getDetailMovie(id: Int): Flowable<Resource<DetailMovies>> = moviesRepository.getDetailMovie(id)

    override fun getAMovie(id: Int): Flowable<Movies> = moviesRepository.getAMovie(id)

    override fun getFavoredMovies(): Flowable<PagedList<Movies>> = moviesRepository.getFavoredMovies()

    override fun setFavorite(movie: Movies, favorite: Boolean, detailMovie: DetailMovies) = moviesRepository.setFavorite(movie, favorite, detailMovie)

}