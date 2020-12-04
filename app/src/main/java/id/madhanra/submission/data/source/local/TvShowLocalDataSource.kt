package id.madhanra.submission.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.local.room.TvShowDao
import id.madhanra.submission.utils.TvShowSortUtils
import javax.inject.Inject

class TvShowLocalDataSource @Inject constructor(private val mTvShowDao: TvShowDao){
    fun getAllTvShow(sort: String): DataSource.Factory<Int, TvShowEntity> {
        val query = TvShowSortUtils.getSortedQuery(sort)
        return mTvShowDao.getTvShows(query)
    }

    fun getDetailTvShow(id: Int): LiveData<DetailTvShowEntity> = mTvShowDao.getDetailTvShow(id)

    fun getATvShow(id: Int): LiveData<TvShowEntity> = mTvShowDao.getATvShow(id)

    fun insertTvShows(tvShows: List<TvShowEntity>) = mTvShowDao.insertTvShows(tvShows)

    fun insertDetailTvShow(tvShow: DetailTvShowEntity) = mTvShowDao.insertDetailTvShow(tvShow)

    fun setFavorite(tvShow: TvShowEntity, favorite: Boolean, detailTvShow: DetailTvShowEntity) {
        tvShow.favorite = favorite
        detailTvShow.favorite = favorite
        mTvShowDao.updateTvShow(tvShow)
        mTvShowDao.updateDetailTvShow(detailTvShow)
    }

    fun getFavoredTvShows(): DataSource.Factory<Int, TvShowEntity> = mTvShowDao.getFavoriteTvShows()
}