package id.madhanra.submission.core.domain.repository

import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.DetailTvShows
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable

interface ITvShowsRepository {
    fun getTvShow(sort: String): Flowable<Resource<PagedList<TvShows>>>

    fun getDetailTvShow(id: Int): Flowable<Resource<DetailTvShows>>

    fun getATvShow(id: Int): Flowable<TvShows>

    fun getFavoredTvShows(): Flowable<List<TvShows>>

    fun setFavorite(tvShow: TvShows, favorite: Boolean, detailTvShow: DetailTvShows)
}