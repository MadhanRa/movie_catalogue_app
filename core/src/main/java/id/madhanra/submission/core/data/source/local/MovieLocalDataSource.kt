package id.madhanra.submission.core.data.source.local

import androidx.paging.DataSource
import id.madhanra.submission.core.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.core.data.source.local.entity.MoviesEntity
import id.madhanra.submission.core.data.source.local.room.MovieDao
import id.madhanra.submission.core.utils.SortUtils
import io.reactivex.Flowable
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    fun getAllMovies(sort: String): DataSource.Factory<Int, MoviesEntity> {
        val query = SortUtils.getSortedQuery(sort)
        return mMovieDao.getMovies(query)
    }

    fun getDetailMovie(id : Int): Flowable<DetailMovieEntity> = mMovieDao.getDetailMovie(id)

    fun getAMovie(id: Int): Flowable<MoviesEntity> = mMovieDao.getAMovie(id)

    fun insertMovies(movies: List<MoviesEntity>) = mMovieDao.insertMovies(movies)

    fun insertDetailMovie(movie: DetailMovieEntity) = mMovieDao.insertDetailMovie(movie)

    fun setFavorite(movie: MoviesEntity, favorite: Boolean, detailMovie: DetailMovieEntity) {
        movie.favorite = favorite
        detailMovie.favorite = favorite
        mMovieDao.updateMovies(movie)
        mMovieDao.updateDetailMovie(detailMovie)
    }

    fun getFavoredMovies(): DataSource.Factory<Int, MoviesEntity> = mMovieDao.getFavoriteMovies()
}