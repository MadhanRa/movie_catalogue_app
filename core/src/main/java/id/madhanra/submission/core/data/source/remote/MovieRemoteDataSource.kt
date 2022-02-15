package id.madhanra.submission.core.data.source.remote

import id.madhanra.submission.core.data.source.remote.response.MoviesItem
import id.madhanra.submission.core.data.source.remote.retrofit.MovieApi
import id.madhanra.submission.core.utils.Const.Companion.NO_INTERNET
import id.madhanra.submission.core.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class MovieRemoteDataSource(
        private val apiService: MovieApi
){
    suspend fun getPopularMovies(page: Int): Flow<ApiResponse<List<MoviesItem>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPopularMovies(page = page)
                val data = response.moviesItem
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(NO_INTERNET))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovie(id: String): Flow<ApiResponse<MoviesItem>>{
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getMovieDetail(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(NO_INTERNET))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSimilarMovies(id: String): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getSimilarMovies(id)
                val data = response.moviesItem

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(NO_INTERNET))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchMovies(keyword: String): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.searchMovie(keyword = keyword)
                val data = response.moviesItem

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(NO_INTERNET))
            }
        }.flowOn(Dispatchers.IO)
    }
}