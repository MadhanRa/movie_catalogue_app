package id.madhanra.submission.core.domain.model

data class Movies (
        val id: Int,
        val overview: String,
        val title: String,
        val posterPath: String,
        val releaseDate: String,
        var favorite: Boolean = false
){
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}