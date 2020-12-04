package id.madhanra.submission.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.madhanra.submission.data.source.remote.response.GenresItem

@Entity (tableName = "detailmovieentity")
data class DetailMovieEntity (
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
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}