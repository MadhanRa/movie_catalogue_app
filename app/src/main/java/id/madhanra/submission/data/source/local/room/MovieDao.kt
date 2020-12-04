package id.madhanra.submission.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [MoviesEntity::class])
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MoviesEntity>

    @Query ("SELECT * FROM detailmovieentity WHERE movieId = :id")
    fun getDetailMovie(id : Int): LiveData<DetailMovieEntity>

    @Query("SELECT * FROM moviesentities WHERE movieId = :id")
    fun getAMovie(id: Int): LiveData<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMovie(movie: DetailMovieEntity)

    @Update
    fun updateMovies(movie: MoviesEntity)

    @Update
    fun updateDetailMovie(movie: DetailMovieEntity)

    @Query("SELECT * FROM moviesentities WHERE favored = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MoviesEntity>

}