package id.madhanra.submission.core.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import id.madhanra.submission.core.data.NetworkBoundResource
import id.madhanra.submission.core.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.core.data.source.local.entity.TvShowEntity
import id.madhanra.submission.core.data.source.local.TvShowLocalDataSource
import id.madhanra.submission.core.data.source.remote.ApiResponse
import id.madhanra.submission.core.data.source.remote.TvShowRemoteDataSource
import id.madhanra.submission.core.data.source.remote.response.DetailTvShowResponse
import id.madhanra.submission.core.data.source.remote.response.TvShowsItem
import id.madhanra.submission.core.domain.model.DetailTvShows
import id.madhanra.submission.core.domain.model.TvShows
import id.madhanra.submission.core.domain.repository.ITvShowsRepository
import id.madhanra.submission.core.utils.AppExecutors
import id.madhanra.submission.core.utils.DataMapper
import id.madhanra.submission.core.vo.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvShowRepository(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val appExecutors: AppExecutors
    ) : ITvShowsRepository {

    override fun getTvShow(sort: String): Flowable<Resource<PagedList<TvShows>>> {
        return object: NetworkBoundResource<PagedList<TvShows>, List<TvShowsItem>>(appExecutors){
            override fun loadFromDb(): Flowable<PagedList<TvShows>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(10)
                    .build()
                return RxPagedListBuilder(tvShowLocalDataSource.getAllTvShow(sort).map { DataMapper.mapTvShowsEntitiesToDomain(it) }, config).buildFlowable(BackpressureStrategy.BUFFER)
            }

            override fun shouldFetch(data: PagedList<TvShows>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<TvShowsItem>>> {
                return tvShowRemoteDataSource.getTvShows()
            }

            override fun saveCallResult(data: List<TvShowsItem>) {
                val tvShowList = ArrayList<TvShowEntity>()

                data.forEach {
                    val tvShowEntity = TvShowEntity(
                        it.id,
                        it.name,
                        it.firstAirDate,
                        it.overview,
                        it.posterPath
                    )
                    tvShowList.add(tvShowEntity)
                }
                tvShowLocalDataSource.insertTvShows(tvShowList).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()).subscribe()
            }
        }.asFlowable()
    }

    override fun getDetailTvShow(id: Int): Flowable<Resource<DetailTvShows>> {
        return object : NetworkBoundResource<DetailTvShows, DetailTvShowResponse>(appExecutors){
            override fun loadFromDb(): Flowable<DetailTvShows> {
                return tvShowLocalDataSource.getDetailTvShow(id).map{
                    val data = if (it.isEmpty()) null else it[0]
                    DataMapper.mapDetailTvShowEntityToDomain(data)
                }
            }

            override fun shouldFetch(data: DetailTvShows?): Boolean {
                return data?.id == 0 || data == null
            }

            override fun createCall(): Flowable<ApiResponse<DetailTvShowResponse>> {
                return tvShowRemoteDataSource.getDetailTvShow(id)
            }

            override fun saveCallResult(data: DetailTvShowResponse) {
                val detailTvShow = DetailTvShowEntity(
                    data.genres,
                    data.firstAirDate,
                    data.overview,
                    data.posterPath,
                    data.voteAverage,
                    data.name,
                    data.tagLine,
                    data.episodeRunTime,
                    data.id
                )
                tvShowLocalDataSource.insertDetailTvShow(detailTvShow).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()).subscribe()
            }
        }.asFlowable()
    }

    override fun getATvShow(id: Int): Flowable<TvShows> {
        return tvShowLocalDataSource.getATvShow(id).map {
            DataMapper.mapTvShowsEntitiesToDomain(it)
        }
    }


    override fun getFavoredTvShows(): Flowable<List<TvShows>> {
        return tvShowLocalDataSource.getFavoredTvShows().map { DataMapper.mapListTvShowEntityToDomain(it) }
    }

    override fun setFavorite(
        tvShow: TvShows,
        favorite: Boolean,
        detailTvShow: DetailTvShows
    ) {
        appExecutors.diskIO().execute{
            val detailTvShowEntity = DataMapper.mapDetailTvShowDomainToEntity(detailTvShow)
            val tvShowsEntities = DataMapper.mapTvShowsDomainToEntity(tvShow)
            tvShowLocalDataSource.setFavorite(tvShowsEntities, favorite, detailTvShowEntity)
        }
    }
}