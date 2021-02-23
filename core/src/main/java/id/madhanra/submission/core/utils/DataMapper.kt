package id.madhanra.submission.core.utils

import id.madhanra.submission.core.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.core.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.core.data.source.local.entity.MoviesEntity
import id.madhanra.submission.core.data.source.local.entity.TvShowEntity
import id.madhanra.submission.core.domain.model.DetailMovies
import id.madhanra.submission.core.domain.model.DetailTvShows
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.domain.model.TvShows

object DataMapper {

    fun mapMoviesEntitiesToDomain(input: MoviesEntity): Movies =
            Movies(
                    id = input.id,
                    overview = input.overview,
                    title = input.title,
                    posterPath = input.posterPath,
                    releaseDate = input.releaseDate,
                    favorite = input.favorite
            )


    fun mapMoviesDomainToEntity(input: Movies) = MoviesEntity(
            id = input.id,
            overview = input.overview,
            title = input.title,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            favorite = input.favorite
    )

    fun mapTvShowsEntitiesToDomain(input: TvShowEntity): TvShows =
            TvShows(
                    id = input.id,
                    name = input.name,
                    firstAirDate = input.firstAirDate,
                    overview = input.overview,
                    posterPath = input.posterPath,
                    favorite = input.favorite
            )


    fun mapTvShowsDomainToEntity(input: TvShows) = TvShowEntity(
            id = input.id,
            name = input.name,
            firstAirDate = input.firstAirDate,
            overview = input.overview,
            posterPath = input.posterPath,
            favorite = input.favorite
    )

    fun mapDetailMovieEntitiesToDomain(input: DetailMovieEntity?) : DetailMovies = DetailMovies(
                title = input?.title ?: "Unknown",
                genres = input?.genres ?: emptyList(),
                overview = input?.overview ?: "Unknown",
                runtime = input?.runtime ?: 0,
                posterPath = input?.posterPath ?: "Unknown",
                releaseDate = input?.releaseDate ?: "Unknown",
                voteAverage = input?.voteAverage ?: 0.0,
                tagLine = input?.tagLine ?: "Unknown",
                id = input?.id ?: 0,
                favorite = input?.favorite ?: false
        )


    fun mapDetailMovieDomainToEntity(input: DetailMovies) =
            DetailMovieEntity(
                    title = input.title,
                    genres = input.genres,
                    overview = input.overview,
                    runtime = input.runtime,
                    posterPath = input.posterPath,
                    releaseDate = input.releaseDate,
                    voteAverage = input.voteAverage,
                    tagLine = input.tagLine,
                    id = input.id,
                    favorite = input.favorite
            )

    fun mapDetailTvShowEntityToDomain(input: DetailTvShowEntity?): DetailTvShows =
            DetailTvShows(
                    genres = input?.genres ?: emptyList(),
                    firstAirDate = input?.firstAirDate ?: "Unknown",
                    overview = input?.overview ?: "Unknown",
                    posterPath = input?.posterPath ?: "Unknown",
                    voteAverage = input?.voteAverage ?: 0.0,
                    name = input?.name ?: "Unknown",
                    tagLine = input?.tagLine ?: "Unknown",
                    episodeRunTime = input?.episodeRunTime ?: emptyList(),
                    id = input?.id ?: 0,
                    favorite = input?.favorite ?: false
            )

    fun mapDetailTvShowDomainToEntity(input: DetailTvShows) =
            DetailTvShowEntity(
                    genres = input.genres,
                    firstAirDate = input.firstAirDate,
                    overview = input.overview,
                    posterPath = input.posterPath,
                    voteAverage = input.voteAverage,
                    name = input.name,
                    tagLine = input.tagLine,
                    episodeRunTime = input.episodeRunTime,
                    id = input.id,
                    favorite = input.favorite
            )

    fun mapListMovieEntityToDomain(input: List<MoviesEntity>): List<Movies> =
            input.map {
                mapMoviesEntitiesToDomain(it)
            }

    fun mapListTvShowEntityToDomain(input: List<TvShowEntity>): List<TvShows> =
            input.map {
                mapTvShowsEntitiesToDomain(it)
            }
}