package id.madhanra.submission.data.source.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.madhanra.submission.data.source.NetworkBoundResource
import id.madhanra.submission.data.source.local.MovieLocalDataSource
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.remote.ApiResponse
import id.madhanra.submission.data.source.remote.MovieRemoteDataSource
import id.madhanra.submission.data.source.remote.response.DetailMovieResponse
import id.madhanra.submission.data.source.remote.response.MoviesItem
import id.madhanra.submission.utils.AppExecutors
import id.madhanra.submission.vo.Resource
import javax.inject.Inject


class MovieRepository @Inject constructor(
        private val movieRemoteDataSource: MovieRemoteDataSource,
        private val movieLocalDataSource: MovieLocalDataSource,
        private val appExecutors: AppExecutors
): MovieDataSource{

    override fun getAllMovies(sort: String): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object: NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesItem>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(movieLocalDataSource.getAllMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MoviesItem>>> {
                return movieRemoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<MoviesItem>) {
                val movieList = ArrayList<MoviesEntity>()

                data.forEach{
                    val movieEntity = MoviesEntity(
                        it.id,
                        it.overview,
                        it.title,
                        it.posterPath,
                        it.releaseDate
                    )
                    movieList.add(movieEntity)
                }
                movieLocalDataSource.insertMovies(movieList)
            }

        }.asLiveData()
    }

    override fun getDetailMovie(id: Int): LiveData<Resource<DetailMovieEntity>> {
        return object: NetworkBoundResource<DetailMovieEntity, DetailMovieResponse>(appExecutors){
            override fun loadFromDb(): LiveData<DetailMovieEntity> {
                return movieLocalDataSource.getDetailMovie(id)
            }

            override fun shouldFetch(data: DetailMovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> {
                return movieRemoteDataSource.getDetailMovie(id)
            }

            override fun saveCallResult(data: DetailMovieResponse) {
                val detailMovie = DetailMovieEntity(
                    data.title,
                    data.genres,
                    data.overview,
                    data.runtime,
                    data.posterPath,
                    data.releaseDate,
                    data.voteAverage,
                    data.tagLine,
                    data.id
                )
                movieLocalDataSource.insertDetailMovie(detailMovie)
            }
        }.asLiveData()
    }

    override fun getAMovie(id: Int): LiveData<MoviesEntity> {
        return movieLocalDataSource.getAMovie(id)
    }

    override fun getFavoredMovies(): LiveData<PagedList<MoviesEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(movieLocalDataSource.getFavoredMovies(), config).build()
    }

    override fun setFavorite(
        movie: MoviesEntity,
        favorite: Boolean,
        detailMovie: DetailMovieEntity
    ) {
        appExecutors.diskIO().execute{
            movieLocalDataSource.setFavorite(movie, favorite, detailMovie)
        }
    }
}

