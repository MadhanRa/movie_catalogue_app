package id.madhanra.submission.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


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
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}

