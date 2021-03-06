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
    fun getDetailTvShow(id : Int): Flowable<List<DetailTvShowEntity>>

    @Query("SELECT * FROM tvshowentities WHERE tvShowId = :id")
    fun getATvShow(id: Int): Flowable<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShows(tvShows: List<TvShowEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTvShow(tvShow: DetailTvShowEntity): Completable

    @Update
    fun updateTvShow(tvShow: TvShowEntity, tvShowDetail: DetailTvShowEntity)

    @Query("SELECT * FROM tvshowentities  WHERE favored = 1")
    fun getFavoriteTvShows(): Flowable<List<TvShowEntity>>
}