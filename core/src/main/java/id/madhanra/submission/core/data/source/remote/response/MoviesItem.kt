package id.madhanra.submission.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MoviesItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("poster_path")
    val posterPath: String,
)