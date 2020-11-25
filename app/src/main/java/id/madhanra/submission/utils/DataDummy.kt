package id.madhanra.submission.utils

import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.remote.response.*


object DataDummy {

    fun generateMovies() : List<MoviesEntity>{
        val movieResult = ArrayList<MoviesEntity>()

        for (x in 1..20) {
            movieResult.add(
                MoviesEntity(
                    x,
                    "Overview$x",
                    "Title$x",
                    "Poster$x",
                    "Date$x"
                )
            )
        }
        return movieResult
    }
    fun generateTvShow() : List<TvShowEntity>{
        val tvShowResult = ArrayList<TvShowEntity>()

        for (x in 1..20) {
            tvShowResult.add(
                TvShowEntity(
                    "Airdate$x",
                    "Overview$x",
                    "Poster$x",
                    "Name$x",
                    x
                )
            )
        }
        return tvShowResult
    }

    fun generateMovieDetail(): DetailMovieEntity {
        val genres = listOf(GenresItem("Genre1"))
        return DetailMovieEntity(
            "Title",
            genres,
            "OverView",
            40,
            "Poster",
            "Date",
            10.0,
            "Tagline",
            1
        )
    }

    fun generateTvShowDetail(): DetailTvShowEntity {
        val runtimeList = listOf(20)
        val genres = listOf(GenresItemTvShow("Genre1"))
        return DetailTvShowEntity(
            genres,
            "Data",
            "OverView",
            "Poster",
            10.0,
            "Name",
            "Tagline",
            runtimeList,
            1
        )
    }

    fun generateRemoteMovies() : MovieResponse{
        val movieResult = ArrayList<MoviesItem>()

        for (x in 1..20) {
            movieResult.add(
                MoviesItem(
                    x,
                    "Overview$x",
                    "Title$x",
                    "Poster$x",
                    "Date$x"
                )
            )
        }
        return MovieResponse(movieResult)
    }

    fun generateRemoteTvShow() : TvShowResponse{
        val tvShowResult = ArrayList<TvShowsItem>()

        for (x in 1..20) {
            tvShowResult.add(
                TvShowsItem(
                    "Airdate$x",
                    "Overview$x",
                    "Poster$x",
                    "Name$x",
                    x
                )
            )
        }
        return TvShowResponse(tvShowResult)
    }

    fun generateRemoteMovieDetail(): DetailMovieResponse {
        val genreList = ArrayList<GenresItem>()
        genreList.add(GenresItem("Genre1"))
        return DetailMovieResponse(
                "Title",
                genreList,
                "OverView",
                40,
                "Poster",
                "Date",
                10.0,
                "Tagline",
                1
        )
    }

    fun generateRemoteTvShowDetail(): DetailTvShowResponse {
        val genres = listOf(GenresItemTvShow("genre1"))
        val runtimeList = listOf(20)
        return DetailTvShowResponse(
                genres,
                "Data",
                "OverView",
                "Poster",
                10.0,
                "Name",
                "Tagline",
                runtimeList,
                1
        )
    }
}