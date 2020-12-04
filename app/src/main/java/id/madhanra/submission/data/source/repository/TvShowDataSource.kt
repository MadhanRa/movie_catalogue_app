package id.madhanra.submission.data.source.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.vo.Resource

interface TvShowDataSource {
    fun getTvShow(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getDetailTvShow(id: Int): LiveData<Resource<DetailTvShowEntity>>

    fun getATvShow(id: Int): LiveData<TvShowEntity>

    fun getFavoredTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavorite(tvShow: TvShowEntity, favorite: Boolean, detailTvShow: DetailTvShowEntity)


}