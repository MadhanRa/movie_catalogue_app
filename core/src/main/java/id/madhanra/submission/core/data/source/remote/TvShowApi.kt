package id.madhanra.submission.core.data.source.remote

import id.madhanra.submission.core.BuildConfig
import id.madhanra.submission.core.data.source.remote.response.DetailTvShowResponse
import id.madhanra.submission.core.data.source.remote.response.TvShowResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("tv/popular?api_key=${API_KEY}")
    fun getPopularTvShow(
        @Query("page")position: Int
    ): Flowable<TvShowResponse>

    @GET("tv/{id}?api_key=${API_KEY}")
    fun getTvShowDetail(
        @Path("id") id: Int
    ): Flowable<DetailTvShowResponse>
}