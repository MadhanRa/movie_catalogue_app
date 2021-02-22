package id.madhanra.submission.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.madhanra.submission.core.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.core.data.source.local.entity.TvShowEntity
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable

interface TvShowDataSource {
    fun getTvShow(sort: String): Flowable<Resource<PagedList<TvShowEntity>>>

    fun getDetailTvShow(id: Int): Flowable<Resource<DetailTvShowEntity>>

    fun getATvShow(id: Int): Flowable<TvShowEntity>

    fun getFavoredTvShows(): Flowable<PagedList<TvShowEntity>>

    fun setFavorite(tvShow: TvShowEntity, favorite: Boolean, detailTvShow: DetailTvShowEntity)


}