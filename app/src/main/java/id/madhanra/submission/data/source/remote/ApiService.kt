package id.madhanra.submission.data.source.remote

import id.madhanra.submission.data.source.remote.response.DetailMovieResponse
import id.madhanra.submission.data.source.remote.response.DetailTvShowResponse
import id.madhanra.submission.data.source.remote.response.MovieResponse
import id.madhanra.submission.data.source.remote.response.TvShowResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "4f7a42b06e6dec6822d5113076d2213c"
    }

    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovies(
        @Query("page")position: Int
    ): Single<MovieResponse>

    @GET("tv/popular?api_key=$API_KEY")
    fun getPopularTvShow(
        @Query("page")position: Int
    ): Single<TvShowResponse>

    @GET("movie/{id}?api_key=${API_KEY}")
    fun getMovieDetail(
        @Path("id") id: Int
    ): Single<DetailMovieResponse>

    @GET("tv/{id}?api_key=${API_KEY}")
    fun getTvShowDetail(
            @Path("id") id: Int
    ): Single<DetailTvShowResponse>

}