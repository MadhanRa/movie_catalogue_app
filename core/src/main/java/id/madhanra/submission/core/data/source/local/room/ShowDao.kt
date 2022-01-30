package id.madhanra.submission.core.data.source.local.room

import androidx.room.*
import id.madhanra.submission.core.data.source.local.entity.ShowEntity
import id.madhanra.submission.core.utils.Const
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {
    @Query("SELECT * FROM showTable WHERE showType = ${Const.MOVIE_TYPE} AND isSearch = 0 LIMIT :page")
    fun getMovies(page: Int): Flow<List<ShowEntity>>

    @Query("SELECT * FROM showTable WHERE showType = ${Const.MOVIE_TYPE} AND isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<ShowEntity>>

    @Query("SELECT * FROM showTable WHERE showType = ${Const.MOVIE_TYPE} AND title LIKE :keyword")
    fun searchMovie(keyword: String): Flow<List<ShowEntity>>

    @Query("SELECT * FROM showTable WHERE showType = ${Const.TV_SHOW_TYPE} AND isSearch = 0 LIMIT :page")
    fun getTvShows(page: Int):Flow<List<ShowEntity>>

    @Query("SELECT * FROM showTable WHERE showType = ${Const.TV_SHOW_TYPE} AND isFavorite = 1")
    fun getFavoriteTvShows(): Flow<List<ShowEntity>>

    @Query("SELECT * FROM showTable WHERE showType = ${Const.TV_SHOW_TYPE} AND title LIKE :keyword")
    fun searchTvShow(keyword: String): Flow<List<ShowEntity>>

    @Query("SELECT * FROM showTable WHERE id = :showId")
    fun getShowById(showId: String): Flow<ShowEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShow(show: List<ShowEntity>)

    @Update
    suspend fun updateShow(show: ShowEntity)

    @Query("DELETE FROM showTable WHERE showType = :showType AND isSearch = 1 AND isFavorite = 0")
    suspend fun deleteAllSearch(showType: Int)
}