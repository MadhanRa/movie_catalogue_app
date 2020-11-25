package id.madhanra.submission.data.source.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowEntity (
    val firstAirDate: String,
    val overview: String,
    val posterPath: String,
    val name: String,
    val id: Int
): Parcelable {
    @IgnoredOnParcel
    val baseUrlPoster : String = "https://image.tmdb.org/t/p/w500"
}