package id.madhanra.submission.core.domain.usecase

import androidx.paging.PagedList
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getAllMovies(page: Int): Flow<Resource<List<Show>>>

    fun getDetailMovie(id: String): Flow<Resource<Show>>

    fun getFavoredMovies(): Flow<Resource<List<Show>>>

    fun getSimilarMovies(id: String): Flow<Resource<List<Show>>>

    fun searchMovie(keyword: String): Flow<Resource<List<Show>>>

    suspend fun setFavorite(show: Show)
}