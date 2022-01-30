package id.madhanra.submission.core.data.source.local

import id.madhanra.submission.core.data.source.local.entity.ShowEntity
import id.madhanra.submission.core.data.source.local.room.ShowDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val showDao: ShowDao) {

    fun getAllMovies(page: Int): Flow<List<ShowEntity>> = showDao.getMovies(page)

    fun getFavoredMovies(): Flow<List<ShowEntity>> = showDao.getFavoriteMovies()

    fun getSearchedMovies(keyword: String): Flow<List<ShowEntity>> = showDao.searchMovie(keyword)

    fun getAllTvShow(page: Int): Flow<List<ShowEntity>> = showDao.getTvShows(page)

    fun getFavoredTvShow(): Flow<List<ShowEntity>> = showDao.getFavoriteTvShows()

    fun getSearchedTvShow(keyword: String): Flow<List<ShowEntity>> = showDao.searchTvShow(keyword)

    fun getShowById(id: String): Flow<ShowEntity> = showDao.getShowById(id)

    suspend fun insertShow(show: List<ShowEntity>) = showDao.insertShow(show)

    suspend fun deleteAllSearchedShow(showType: Int) = showDao.deleteAllSearch(showType)

    suspend fun setFavorite(show: ShowEntity) = showDao.updateShow(show)

}