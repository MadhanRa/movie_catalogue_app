package id.madhanra.submission.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.madhanra.submission.core.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.core.data.source.local.entity.TvShowEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TvShowDao {
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM detailtvshowentity WHERE tvShowId = :id")
    fun getDetailTvShow(id : Int): Flowable<DetailTvShowEntity>

    @Query("SELECT * FROM tvshowentities WHERE tvShowId = :id")
    fun getATvShow(id: Int): Flowable<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTvShow(tvShow: DetailTvShowEntity): Completable

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Update
    fun updateDetailTvShow(tvShow: DetailTvShowEntity)

    @Query("SELECT * FROM tvshowentities  WHERE favored = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>
}