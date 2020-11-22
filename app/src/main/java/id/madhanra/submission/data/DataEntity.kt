package id.madhanra.submission.data

data class DataEntity (
    var id: String,
    var title: String,
    var overview: String,
    var year: String,
    var length: String,
    var genre: String,
    var tagline: String,
    var certification: String,
    var bookmarked: Boolean = false,
    var userScore: String,
    var imagePath: String
)