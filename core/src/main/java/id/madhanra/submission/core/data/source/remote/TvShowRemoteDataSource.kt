package id.madhanra.submission.core.data.source.remote

import id.madhanra.submission.core.data.source.remote.response.TvShowsItem
import id.madhanra.submission.core.data.source.remote.retrofit.TvShowApi
import id.madhanra.submission.core.utils.Const.Companion.NO_INTERNET
import id.madhanra.submission.core.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class TvShowRemoteDataSource(
    private val apiService: TvShowApi
) {

    fun getTvShows(page: Int): Flow<ApiResponse<List<TvShowsItem>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPopularTvShow(page = page)
                val data = response.tvShowsItem

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

    fun getDetailTvShow(id: String): Flow<ApiResponse<TvShowsItem>>{
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getTvShowDetail(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(NO_INTERNET))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSimilarTvShows(id: String): Flow<ApiResponse<List<TvShowsItem>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getSimilarTvShow(id)
                val data = response.tvShowsItem

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

    fun searchTvShows(keyword: String): Flow<ApiResponse<List<TvShowsItem>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.searchTvShow(keyword = keyword)
                val data = response.tvShowsItem

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