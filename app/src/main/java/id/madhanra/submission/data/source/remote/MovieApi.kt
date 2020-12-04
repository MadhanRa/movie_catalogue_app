package id.madhanra.submission.data.source.remote

import id.madhanra.submission.BuildConfig
import id.madhanra.submission.data.source.remote.response.DetailMovieResponse
import id.madhanra.submission.data.source.remote.response.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovies(
        @Query("page")position: Int
    ): Single<MovieResponse>

    @GET("movie/{id}?api_key=${API_KEY}")
    fun getMovieDetail(
        @Path("id") id: Int
    ): Single<DetailMovieResponse>

}