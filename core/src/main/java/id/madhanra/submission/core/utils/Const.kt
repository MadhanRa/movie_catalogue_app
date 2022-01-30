package id.madhanra.submission.core.utils

class Const {
    companion object {
        const val VIEW_MODEL = "ViewModel"

        // URL For Load Image
        const val URL_BASE_IMAGE = "https://image.tmdb.org/t/p/w500"

        // Size for Poster Image
        const val POSTER_TARGET_WIDTH = 200
        const val POSTER_TARGET_HEIGHT = 300

        // Size for Backdrop Image
        const val BACKDROP_TARGET_WIDTH = 1280
        const val BACKDROP_TARGET_HEIGHT = 720

        // Indicator for Knowing show type (Movie/Series)
        const val MOVIE_TYPE = 0
        const val TV_SHOW_TYPE = 1

        // For Null Data
        const val UNKNOWN_VALUE = "Unknown"

        // Error Message
        const val NO_INTERNET = "Internet connection issue."

        const val IF_MOVIE = 100
    }
}