package id.madhanra.submission.core.domain.model

import id.madhanra.submission.core.data.source.remote.response.GenresItemTvShow

data class DetailTvShows(
        val genres: List<GenresItemTvShow>,
        val firstAirDate: String?,
        val overview: String?,
        val posterPath: String?,
        val voteAverage: Double?,
        val name: String?,
        val tagLine: String?,
        val episodeRunTime: List<Int>?,
        val id: Int?,
        var favorite: Boolean = false
){
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}