package id.madhanra.submission.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.*
import id.madhanra.submission.core.data.source.remote.response.GenresItem

@Entity (tableName = "detailmovieentity")
data class DetailMovieEntity(
        @ColumnInfo(name = "movieTitle")
        val title: String?,

        @ColumnInfo(name = "movieGenres")
        val genres: List<GenresItem>?,

        @ColumnInfo(name = "movieOverview")
        val overview: String?,

        @ColumnInfo(name = "movieRuntime")
        val runtime: Int?,

        @ColumnInfo(name = "moviePosterPath")
        val posterPath: String?,

        @ColumnInfo(name = "movieReleaseDate")
        val releaseDate: String?,

        @ColumnInfo(name = "movieVoteAverage")
        val voteAverage: Double?,

        @ColumnInfo(name = "movieTagline")
        val tagLine: String?,

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "movieId")
        val id: Int?,

        @ColumnInfo(name = "favored")
        var favorite: Boolean = false
){
    @Embedded
    @Ignore
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}