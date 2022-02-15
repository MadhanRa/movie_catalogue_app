package id.madhanra.submission.core.data.source.remote.retrofit

import id.madhanra.submission.core.BuildConfig
import id.madhanra.submission.core.data.source.remote.response.MovieListResponse
import id.madhanra.submission.core.data.source.remote.response.MoviesItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api: String? = API_KEY,
        @Query("page") page: Int
    ): MovieListResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: String,
        @Query("api_key") api: String? = API_KEY
    ): MoviesItem

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id: String,
        @Query("api_key") api: String? = API_KEY
    ): MovieListResponse

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") api: String? = API_KEY,
        @Query("query") keyword: String
    ): MovieListResponse
}