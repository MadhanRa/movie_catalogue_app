package id.madhanra.submission.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.madhanra.submission.core.data.source.remote.response.GenresItemTvShow

@Entity (tableName = "detailtvshowentity")
data class DetailTvShowEntity (
    @ColumnInfo(name = "tvShowGenres")
    val genres: List<GenresItemTvShow>,

    @ColumnInfo(name = "tvShowFirstAir")
    val firstAirDate: String?,

    @ColumnInfo(name = "tvShowOverview")
    val overview: String?,

    @ColumnInfo(name = "tvShowPoster")
    val posterPath: String?,

    @ColumnInfo(name = "tvShowVote")
    val voteAverage: Double?,

    @ColumnInfo(name = "tvShowName")
    val name: String?,

    @ColumnInfo(name = "tvShowTagLine")
    val tagLine: String?,

    @ColumnInfo(name = "tvShowEpisodeRunTime")
    val episodeRunTime: List<Int>?,

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    val id: Int?,

    @ColumnInfo(name = "favored")
    var favorite: Boolean = false
){
    @Embedded
    var baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}