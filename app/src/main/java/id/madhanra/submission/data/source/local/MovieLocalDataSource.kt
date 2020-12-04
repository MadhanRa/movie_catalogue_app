package id.madhanra.submission.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.local.room.MovieDao
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    fun getAllMovies(): DataSource.Factory<Int, MoviesEntity> = mMovieDao.getMovies()

    fun getDetailMovie(id : Int): LiveData<DetailMovieEntity> = mMovieDao.getDetailMovie(id)

    fun getAMovie(id: Int): LiveData<MoviesEntity> = mMovieDao.getAMovie(id)

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