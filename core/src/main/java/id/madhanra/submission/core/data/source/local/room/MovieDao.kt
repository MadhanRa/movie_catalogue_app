package id.madhanra.submission.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.madhanra.submission.core.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.core.data.source.local.entity.MoviesEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [MoviesEntity::class])
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MoviesEntity>

    @Query ("SELECT * FROM detailmovieentity WHERE movieId = :id")
    fun getDetailMovie(id : Int): Flowable<DetailMovieEntity>

    @Query("SELECT * FROM moviesentities WHERE movieId = :id")
    fun getAMovie(id: Int): Flowable<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMovie(movie: DetailMovieEntity) : Completable

    @Update
    fun updateMovies(movie: MoviesEntity)

    @Update
    fun updateDetailMovie(movie: DetailMovieEntity)

    @Query("SELECT * FROM moviesentities WHERE favored = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MoviesEntity>

}