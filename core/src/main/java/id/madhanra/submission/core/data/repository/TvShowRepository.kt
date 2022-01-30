package id.madhanra.submission.core.data.repository

import id.madhanra.submission.core.data.LocalResource
import id.madhanra.submission.core.data.NetworkBoundResource
import id.madhanra.submission.core.data.RemoteResource
import id.madhanra.submission.core.data.source.remote.ApiResponse
import id.madhanra.submission.core.data.source.remote.TvShowRemoteDataSource
import id.madhanra.submission.core.data.source.remote.response.TvShowsItem
import id.madhanra.submission.core.domain.repository.ITvShowsRepository
import id.madhanra.submission.core.utils.DataMapper
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.data.source.local.LocalDataSource
import id.madhanra.submission.core.data.source.local.entity.ShowEntity
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.utils.Const
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TvShowRepository(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: LocalDataSource
) : ITvShowsRepository {

    override fun getAllTvShows(page: Int): Flow<Resource<List<Show>>> {
        return object : NetworkBoundResource<List<Show>, List<TvShowsItem>>() {
            override fun loadFromDb(): Flow<List<Show>> {
                return localDataSource.getAllTvShow(page * 20).map {
                    DataMapper.mapListEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Show>?): Boolean =
                data == null || data.isEmpty() || data.size != page * 20

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowsItem>>> =
                remoteDataSource.getTvShows(page)

            override suspend fun saveCallResult(data: List<TvShowsItem>) {
                val tvShowList = ArrayList<ShowEntity>()

                data.map {
                    val tvShow = DataMapper.mapTvShowResponseToEntity(it)
                    tvShowList.add(tvShow)
                }

                localDataSource.insertShow(tvShowList)
            }
        }.asFlow()
    }

    override fun getDetailTvShow(id: String): Flow<Resource<Show>> {
        return object : NetworkBoundResource<Show, TvShowsItem>() {
            override fun loadFromDb(): Flow<Show> {
                return localDataSource.getShowById(id).map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Show?): Boolean =
                data == null || data.id == Const.UNKNOWN_VALUE

            override suspend fun createCall(): Flow<ApiResponse<TvShowsItem>> =
                remoteDataSource.getDetailTvShow(id)

            override suspend fun saveCallResult(data: TvShowsItem) {
                val tvShow = DataMapper.mapTvShowResponseToEntity(data)
                localDataSource.insertShow(listOf(tvShow))
            }
        }.asFlow()
    }

    override fun getFavoredTvShows(): Flow<Resource<List<Show>>> {
        return object : LocalResource<List<Show>>() {
            override fun loadFromDB(): Flow<List<Show>> {
                return localDataSource.getFavoredTvShow().map {
                    DataMapper.mapListEntityToDomain(it)
                }
            }
        }.asFlow()
    }

    override fun getSimilarTvShows(id: String): Flow<Resource<List<Show>>> {
        return object : RemoteResource<List<Show>, List<TvShowsItem>>() {
            override fun createCall(): Flow<ApiResponse<List<TvShowsItem>>> =
                remoteDataSource.getSimilarTvShows(id)

            override fun convertCallResult(data: List<TvShowsItem>): Flow<List<Show>> {
                val result = data.map {
                    DataMapper.mapTvShowResponseToDomain(it)
                }
                return flow { emit(result) }
            }

            override fun emptyResult(): Flow<List<Show>> =
                flow { emit(emptyList()) }
        }.asFlow()
    }

    override fun searchTvShow(keyword: String): Flow<Resource<List<Show>>> {
        return object : NetworkBoundResource<List<Show>, List<TvShowsItem>>() {
            override fun loadFromDb(): Flow<List<Show>> {
                return localDataSource.getSearchedTvShow("$keyword%").map {
                    DataMapper.mapListEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Show>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowsItem>>> =
                remoteDataSource.searchTvShows(keyword)

            override suspend fun saveCallResult(data: List<TvShowsItem>) {
                val tvShowList = ArrayList<ShowEntity>()
                val isSearch = 1

                data.map {
                    val tvShow = DataMapper.mapTvShowResponseToEntity(it, isSearch = isSearch)
                    tvShowList.add(tvShow)
                }

                // Delete old search result
                localDataSource.deleteAllSearchedShow(Const.TV_SHOW_TYPE)

                // Insert new Search result
                localDataSource.insertShow(tvShowList)
            }
        }.asFlow()
    }

    override suspend fun setFavorite(show: Show) {
        val tvShowEntity = DataMapper.mapDomainToEntity(show)
        localDataSource.setFavorite(tvShowEntity)
    }


}