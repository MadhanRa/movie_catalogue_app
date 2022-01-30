package id.madhanra.submission.core.domain.usecase

import id.madhanra.submission.core.domain.repository.ITvShowsRepository
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import kotlinx.coroutines.flow.Flow

class TvShowsInteractor(private val tvShowsRepository: ITvShowsRepository): TvShowsUseCase {
    override fun getAllTvShows(page: Int): Flow<Resource<List<Show>>> = tvShowsRepository.getAllTvShows(page)

    override fun getDetailTvShow(id: String): Flow<Resource<Show>> = tvShowsRepository.getDetailTvShow(id)

    override fun getFavoredTvShows(): Flow<Resource<List<Show>>> = tvShowsRepository.getFavoredTvShows()

    override fun getSimilarTvShows(id: String): Flow<Resource<List<Show>>> = tvShowsRepository.getSimilarTvShows(id)

    override fun searchTvShow(keyword: String): Flow<Resource<List<Show>>> = tvShowsRepository.searchTvShow(keyword)

    override suspend fun setFavorite(show: Show) = tvShowsRepository.setFavorite(show)

}