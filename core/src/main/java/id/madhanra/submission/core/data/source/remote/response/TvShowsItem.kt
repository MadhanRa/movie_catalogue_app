package id.madhanra.submission.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("poster_path")
    val posterPath: String,
)