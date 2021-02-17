package id.madhanra.submission.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("tagline")
	val tagLine: String,

	val id: Int
)
data class GenresItem(
	@field:SerializedName("name")
	val name: String
)


