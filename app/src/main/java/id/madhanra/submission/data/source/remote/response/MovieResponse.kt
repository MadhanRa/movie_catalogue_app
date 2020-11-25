package id.madhanra.submission.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

data class MovieResponse(

	@field:SerializedName("results")
	val results: List<MoviesItem>

)

data class MoviesItem(

	val id: Int,

	@field:SerializedName("overview")
	val overview: String,


	@field:SerializedName("title")
	val title: String,


	@field:SerializedName("poster_path")
	val posterPath: String,


	@field:SerializedName("release_date")
	val releaseDate: String

)
