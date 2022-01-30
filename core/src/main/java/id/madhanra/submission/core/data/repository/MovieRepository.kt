package id.madhanra.submission.core.data.repository

import id.madhanra.submission.core.data.LocalResource
import id.madhanra.submission.core.data.NetworkBoundResource
import id.madhanra.submission.core.data.RemoteResource
import id.madhanra.submission.core.data.source.local.LocalDataSource
import id.madhanra.submission.core.data.source.remote.ApiResponse
import id.madhanra.submission.core.data.source.remote.MovieRemoteDataSource
import id.madhanra.submission.core.domain.repository.IMoviesRepository
import id.madhanra.submission.core.utils.DataMapper
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.data.source.local.entity.ShowEntity
import id.madhanra.submission.core.data.source.remote.response.MoviesItem
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.utils.Const
import kotlinx.coroutines.flow.*

class MovieRepository(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMoviesRepository {

    override fun getAllMovies(page: Int): Flow<Resource<List<Show>>> {
        return object : NetworkBoundResource<List<Show>, List<MoviesItem>>() {
            override fun loadFromDb(): Flow<List<Show>> {
                return localDataSource.getAllMovies(page * 20)
                    .map { DataMapper.mapListEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<Show>?): Boolean =
                data == null || data.isEmpty() || data.size != page * 20

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getMovies(page)

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                val movieList = ArrayList<ShowEntity>()
                data.map {
                    val movie = DataMapper.mapMovieResponseToEntity(it)
                    movieList.add(movie)
                }

                localDataSource.insertShow(movieList)
            }
        }.asFlow()
    }

    override fun getDetailMovie(id: String): Flow<Resource<Show>> {
        return object : NetworkBoundResource<Show, MoviesItem>() {
            override fun loadFromDb(): Flow<Show> {
                return localDataSource.getShowById(id).map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Show?): Boolean =
                data == null || data.id == Const.UNKNOWN_VALUE

            override suspend fun createCall(): Flow<ApiResponse<MoviesItem>> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveCallResult(data: MoviesItem) {
                val movie = DataMapper.mapMovieResponseToEntity(data)
                localDataSource.insertShow(listOf(movie))
            }
        }.asFlow()
    }

    override fun getFavoredMovies(): Flow<Resource<List<Show>>> {
        return object : LocalResource<List<Show>>() {
            override fun loadFromDB(): Flow<List<Show>> =
                localDataSource.getFavoredMovies().map {
                    DataMapper.mapListEntityToDomain(it)
                }
        }.asFlow()
    }

    override fun getSimilarMovies(id: String): Flow<Resource<List<Show>>> {
        return object : RemoteResource<List<Show>, List<MoviesItem>>() {
            override fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getSimilarMovies(id)

            override fun convertCallResult(data: List<MoviesItem>): Flow<List<Show>> {
                val result = data.map {
                    DataMapper.mapMovieResponseToDomain(it)
                }
                return flow { emit(result) }
            }

            override fun emptyResult(): Flow<List<Show>> = flow { emit(emptyList()) }
        }.asFlow()
    }

    override fun searchMovie(keyword: String): Flow<Resource<List<Show>>> {
        return object : NetworkBoundResource<List<Show>, List<MoviesItem>>() {
            override fun loadFromDb(): Flow<List<Show>> {
                return localDataSource.getSearchedMovies("$keyword%").map {
                    DataMapper.mapListEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Show>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.searchMovies(keyword)

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                val movieList = ArrayList<ShowEntity>()
                val isSearch = 1

                data.map {
                    val movie = DataMapper.mapMovieResponseToEntity(it, isSearch = isSearch)
                    movieList.add(movie)
                }

                // Delete old search result
                localDataSource.deleteAllSearchedShow(Const.MOVIE_TYPE)

                // Insert new search result
                localDataSource.insertShow(movieList)
            }
        }.asFlow()
    }

    override suspend fun setFavorite(show: Show) {
        val movieEntity = DataMapper.mapDomainToEntity(show)
        localDataSource.setFavorite(movieEntity)
    }


}

