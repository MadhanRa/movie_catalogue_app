package id.madhanra.submission.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tvshowentities")
    fun getTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM detailtvshowentity WHERE tvShowId = :id")
    fun getDetailTvShow(id : Int): LiveData<DetailTvShowEntity>

    @Query("SELECT * FROM tvshowentities WHERE tvShowId = :id")
    fun getATvShow(id: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTvShow(tvShow: DetailTvShowEntity)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Update
    fun updateDetailTvShow(tvShow: DetailTvShowEntity)

    @Query("SELECT * FROM tvshowentities  WHERE favored = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>
}