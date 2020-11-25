package id.madhanra.submission.data.source.local.entity

import id.madhanra.submission.data.source.remote.response.GenresItem

data class DetailMovieEntity (
        val title: String?,
        val genres: List<GenresItem>?,
        val overview: String?,
        val runtime: Int?,
        val posterPath: String?,
        val releaseDate: String?,
        val voteAverage: Double?,
        val tagLine: String?,
        val id: Int?
){
    val baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}