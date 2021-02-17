package id.madhanra.submission.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
	@field:SerializedName("results")
	val results: List<TvShowsItem>,
)

data class TvShowsItem(

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
