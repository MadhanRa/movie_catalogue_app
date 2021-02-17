package id.madhanra.submission.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailTvShowResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItemTvShow>,

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("tagline")
	val tagLine: String,

	@field:SerializedName("episode_run_time")
	val episodeRunTime: List<Int>,

	val id: Int
)

data class GenresItemTvShow(
	@field:SerializedName("name")
	val name: String
)