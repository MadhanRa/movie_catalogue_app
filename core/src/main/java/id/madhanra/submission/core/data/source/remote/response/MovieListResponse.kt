package id.madhanra.submission.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(

	@field:SerializedName("results")
	val moviesItem: List<MoviesItem>

)


