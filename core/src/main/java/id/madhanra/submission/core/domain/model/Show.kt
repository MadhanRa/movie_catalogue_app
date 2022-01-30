package id.madhanra.submission.core.domain.model

data class Show (
    var id: String,
    var title: String,
    var overview: String,
    var releaseDate: String,
    var posterPath: String,
    var backdropPath: String,
    var showType: Int,
    var isFavorite: Int,
    var isSearch: Int
    )