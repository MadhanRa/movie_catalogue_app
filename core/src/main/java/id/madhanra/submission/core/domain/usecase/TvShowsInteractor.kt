package id.madhanra.submission.core.domain.usecase

import androidx.paging.PagedList
import id.madhanra.submission.core.domain.model.DetailTvShows
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.repository.ITvShowsRepository
import id.madhanra.submission.core.vo.Resource
import io.reactivex.Flowable
import javax.inject.Inject

class TvShowsInteractor @Inject constructor(private val tvShowsRepository: ITvShowsRepository): TvShowsUseCase {
    override fun getTvShow(sort: String): Flowable<Resource<PagedList<TvShows>>> = tvShowsRepository.getTvShow(sort)

    override fun getDetailTvShow(id: Int): Flowable<Resource<DetailTvShows>> = tvShowsRepository.getDetailTvShow(id)

    override fun getATvShow(id: Int): Flowable<TvShows> = tvShowsRepository.getATvShow(id)

    override fun getFavoredTvShows(): Flowable<PagedList<TvShows>> = tvShowsRepository.getFavoredTvShows()

    override fun setFavorite(tvShow: TvShows, favorite: Boolean, detailTvShow: DetailTvShows) = tvShowsRepository.setFavorite(tvShow, favorite, detailTvShow)

}