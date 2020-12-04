package id.madhanra.submission.data.source.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.madhanra.submission.data.source.NetworkBoundResource
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.local.TvShowLocalDataSource
import id.madhanra.submission.data.source.remote.ApiResponse
import id.madhanra.submission.data.source.remote.TvShowRemoteDataSource
import id.madhanra.submission.data.source.remote.response.DetailTvShowResponse
import id.madhanra.submission.data.source.remote.response.TvShowsItem
import id.madhanra.submission.utils.AppExecutors
import id.madhanra.submission.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowRepository @Inject constructor(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val appExecutors: AppExecutors
    ) : TvShowDataSource {

    override fun getTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object: NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowsItem>>(appExecutors){
            override fun loadFromDb(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(tvShowLocalDataSource.getAllTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowsItem>>> {
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
                tvShowLocalDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getDetailTvShow(id: Int): LiveData<Resource<DetailTvShowEntity>> {
        return object : NetworkBoundResource<DetailTvShowEntity, DetailTvShowResponse>(appExecutors){
            override fun loadFromDb(): LiveData<DetailTvShowEntity> {
                return tvShowLocalDataSource.getDetailTvShow(id)
            }

            override fun shouldFetch(data: DetailTvShowEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<DetailTvShowResponse>> {
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
                tvShowLocalDataSource.insertDetailTvShow(detailTvShow)
            }
        }.asLiveData()
    }

    override fun getATvShow(id: Int): LiveData<TvShowEntity> {
        return tvShowLocalDataSource.getATvShow(id)
    }


    override fun getFavoredTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(tvShowLocalDataSource.getFavoredTvShows(), config).build()
    }

    override fun setFavorite(
        tvShow: TvShowEntity,
        favorite: Boolean,
        detailTvShow: DetailTvShowEntity
    ) {
        appExecutors.diskIO().execute{
            tvShowLocalDataSource.setFavorite(tvShow, favorite, detailTvShow)
        }
    }
}