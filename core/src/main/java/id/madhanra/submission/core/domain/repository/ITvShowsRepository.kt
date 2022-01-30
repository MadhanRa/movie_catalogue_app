package id.madhanra.submission.core.domain.repository

import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface ITvShowsRepository {
    fun getAllTvShows(page: Int): Flow<Resource<List<Show>>>

    fun getDetailTvShow(id: String): Flow<Resource<Show>>

    fun getFavoredTvShows(): Flow<Resource<List<Show>>>

    fun getSimilarTvShows(id: String): Flow<Resource<List<Show>>>

    fun searchTvShow(keyword: String): Flow<Resource<List<Show>>>

    suspend fun setFavorite(show: Show)
}