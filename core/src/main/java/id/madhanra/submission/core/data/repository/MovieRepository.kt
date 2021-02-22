package id.madhanra.submission.core.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import id.madhanra.submission.core.data.NetworkBoundResource
import id.madhanra.submission.core.data.source.local.MovieLocalDataSource
import id.madhanra.submission.core.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.core.data.source.local.entity.MoviesEntity
import id.madhanra.submission.core.data.source.remote.ApiResponse
import id.madhanra.submission.core.data.source.remote.MovieRemoteDataSource
import id.madhanra.submission.core.data.source.remote.response.DetailMovieResponse
import id.madhanra.submission.core.data.source.remote.response.MoviesItem
import id.madhanra.submission.core.domain.model.DetailMovies
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.repository.IMoviesRepository
import id.madhanra.submission.core.utils.AppExecutors
import id.madhanra.submission.core.utils.DataMapper
import id.madhanra.submission.core.vo.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MovieRepository @Inject constructor(
        private val movieRemoteDataSource: MovieRemoteDataSource,
        private val movieLocalDataSource: MovieLocalDataSource,
        private val appExecutors: AppExecutors
): IMoviesRepository{

    override fun getAllMovies(sort: String): Flowable<Resource<PagedList<Movies>>> {
        return object: NetworkBoundResource<PagedList<Movies>, List<MoviesItem>>(appExecutors) {
            override fun loadFromDb(): Flowable<PagedList<Movies>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(10)
                    .build()
                return RxPagedListBuilder(movieLocalDataSource.getAllMovies(sort).map {DataMapper.mapMoviesEntitiesToDomain(it)}, config).buildFlowable(BackpressureStrategy.BUFFER)
            }

            override fun shouldFetch(data: PagedList<Movies>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<MoviesItem>>> {
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
                movieLocalDataSource.insertMovies(movieList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
            }

        }.asFlowable()
    }

    override fun getDetailMovie(id: Int): Flowable<Resource<DetailMovies>> {
        return object: NetworkBoundResource<DetailMovies, DetailMovieResponse>(appExecutors){
            override fun loadFromDb(): Flowable<DetailMovies> {
                return movieLocalDataSource.getDetailMovie(id).map{
                    val data = if (it.isEmpty()) null else it[0]
                    DataMapper.mapDetailMovieEntitiesToDomain(data)
                }
            }

            override fun shouldFetch(data: DetailMovies?): Boolean {
                return data?.id == 0 || data == null
            }

            override fun createCall(): Flowable<ApiResponse<DetailMovieResponse>> {
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
                movieLocalDataSource.insertDetailMovie(detailMovie).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
            }
        }.asFlowable()
    }

    override fun getAMovie(id: Int): Flowable<Movies> {
        return movieLocalDataSource.getAMovie(id).map {
            DataMapper.mapMoviesEntitiesToDomain(it)
        }
    }

    override fun getFavoredMovies(): Flowable<PagedList<Movies>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()
        return RxPagedListBuilder(movieLocalDataSource.getFavoredMovies().map { DataMapper.mapMoviesEntitiesToDomain(it) }, config).buildFlowable(BackpressureStrategy.BUFFER)
    }

    override fun setFavorite(
        movie: Movies,
        favorite: Boolean,
        detailMovie: DetailMovies
    ) {
        appExecutors.diskIO().execute{
            val detailMovieEntities = DataMapper.mapDetailMovieDomainToEntity(detailMovie)
            val moviesEntities = DataMapper.mapMoviesDomainToEntity(movie)
            movieLocalDataSource.setFavorite(moviesEntities, favorite, detailMovieEntities)
        }
    }
}

