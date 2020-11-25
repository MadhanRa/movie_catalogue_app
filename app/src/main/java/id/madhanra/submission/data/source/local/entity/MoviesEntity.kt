package id.madhanra.submission.data.source.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesEntity (
    val id: Int,
    val overview: String,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
): Parcelable {
    @IgnoredOnParcel
    val baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}

