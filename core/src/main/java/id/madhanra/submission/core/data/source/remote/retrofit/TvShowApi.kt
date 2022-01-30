package id.madhanra.submission.core.data.source.remote.retrofit

import id.madhanra.submission.core.BuildConfig
import id.madhanra.submission.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("tv/popular")
    suspend fun getPopularTvShow(
        @Query("page") page: Int,
        @Query("api_key") api: String? = API_KEY
    ): TvShowListResponse

    @GET("tv/{id}")
    suspend fun getTvShowDetail(
        @Path("id") id: String,
        @Query("api_key") api: String? = API_KEY
    ): TvShowsItem

    @GET("tv/{id}/similar")
    suspend fun getSimilarTvShow(
        @Path("id") id: String,
        @Query("api_key") api: String? = API_KEY
    ): TvShowListResponse

    @GET("/3/search/tv")
    suspend fun searchTvShow(
        @Query("api_key") api: String? = API_KEY,
        @Query("query") keyword: String
    ): TvShowListResponse
}