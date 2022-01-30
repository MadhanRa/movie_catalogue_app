package id.madhanra.submission.core.domain.usecase

import id.madhanra.submission.core.domain.repository.IMoviesRepository
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val moviesRepository: IMoviesRepository): MoviesUseCase {
    override fun getAllMovies(page: Int): Flow<Resource<List<Show>>> = moviesRepository.getAllMovies(page)

    override fun getDetailMovie(id: String): Flow<Resource<Show>> = moviesRepository.getDetailMovie(id)

    override fun getFavoredMovies(): Flow<Resource<List<Show>>> = moviesRepository.getFavoredMovies()

    override fun getSimilarMovies(id: String): Flow<Resource<List<Show>>> = moviesRepository.getSimilarMovies(id)

    override fun searchMovie(keyword: String): Flow<Resource<List<Show>>> = moviesRepository.searchMovie(keyword)

    override suspend fun setFavorite(show: Show) = moviesRepository.setFavorite(show)

}