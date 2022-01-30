package id.madhanra.submission.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowListResponse(
	@field:SerializedName("results")
	val tvShowsItem: List<TvShowsItem>,
)
