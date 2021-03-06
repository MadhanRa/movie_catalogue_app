package id.madhanra.submission.core.domain.usecase

import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.DetailTvShows
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.repository.ITvShowsRepository
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable

class TvShowsInteractor(private val tvShowsRepository: ITvShowsRepository): TvShowsUseCase {
    override fun getTvShow(page: Int, sort: String): Flowable<Resource<PagedList<TvShows>>> = tvShowsRepository.getTvShow(page, sort)

    override fun getDetailTvShow(id: Int): Flowable<Resource<DetailTvShows>> = tvShowsRepository.getDetailTvShow(id)

    override fun getATvShow(id: Int): Flowable<TvShows> = tvShowsRepository.getATvShow(id)

    override fun getFavoredTvShows(): Flowable<List<TvShows>> = tvShowsRepository.getFavoredTvShows()

    override fun setFavorite(tvShow: TvShows, favorite: Boolean, detailTvShow: DetailTvShows) = tvShowsRepository.setFavorite(tvShow, favorite, detailTvShow)

}