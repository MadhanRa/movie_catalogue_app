package id.madhanra.submission.core.domain.model

import id.madhanra.submission.core.data.source.remote.response.GenresItem

data class DetailMovies(
        val title: String?,
        val genres: List<GenresItem>?,
        val overview: String?,
        val runtime: Int?,
        val posterPath: String?,
        val releaseDate: String?,
        val voteAverage: Double?,
        val tagLine: String?,
        val id: Int?,
        var favorite: Boolean = false
){
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}
