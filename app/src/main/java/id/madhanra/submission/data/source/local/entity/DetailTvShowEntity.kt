package id.madhanra.submission.data.source.local.entity

import id.madhanra.submission.data.source.remote.response.GenresItemTvShow

data class DetailTvShowEntity (
    val genres: List<GenresItemTvShow>,
    val firstAirDate: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val name: String?,
    val tagLine: String?,
    val episodeRunTime: List<Int>?,
    val id: Int?
){
    val baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}