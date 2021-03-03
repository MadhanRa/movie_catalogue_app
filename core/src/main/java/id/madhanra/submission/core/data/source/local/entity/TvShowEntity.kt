package id.madhanra.submission.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "tvshowentities")
data class TvShowEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    val id: Int,

    @ColumnInfo(name = "tvShowName")
    val name: String,

    @ColumnInfo(name = "tvShowFirstAirDate")
    val firstAirDate: String,

    @ColumnInfo(name = "tvShowOverview")
    val overview: String,

    @ColumnInfo(name = "tvShowPosterPath")
    val posterPath: String,

    @ColumnInfo(name = "favored")
    var favorite: Boolean = false
) {
    @Embedded
    @Ignore
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}