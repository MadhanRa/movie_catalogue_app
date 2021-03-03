package id.madhanra.submission.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "moviesentities")
data class MoviesEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    val id: Int,

    @ColumnInfo(name = "movieOverview")
    val overview: String,

    @ColumnInfo(name = "movieTitle")
    val title: String,

    @ColumnInfo(name = "moviePosterPath")
    val posterPath: String,

    @ColumnInfo(name = "movieReleaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "favored")
    var favorite: Boolean = false
) {
    @Embedded
    @Ignore
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}

