package id.madhanra.submission.core.domain.model

data class TvShows(
        val id: Int,
        val name: String,
        val firstAirDate: String,
        val overview: String,
        val posterPath: String,
        var favorite: Boolean = false
) {
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}
