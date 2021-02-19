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

    fun mapDetailMovieEntitiesToDomain(input: List<DetailMovieEntity>) : DetailMovies? {
        if (input.isEmpty()){
            return null
        }else{
            return DetailMovies(
                    title = input[0].title ?: "Unknown",
                    genres = input[0].genres ?: ArrayList(),
                    overview = input[0].overview ?: "Unknown",
                    runtime = input[0].runtime ?: 0,
                    posterPath = input[0].posterPath ?: "Unknown",
                    releaseDate = input[0].releaseDate ?: "Unknown",
                    voteAverage = input[0].voteAverage ?: 0.0,
                    tagLine = input[0].tagLine ?: "Unknown",
                    id = input[0].id ?: 0,
                    favorite = input[0].favorite ?: false
            )
        }
    }

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
                    genres = input?.genres ?: ArrayList(),
                    firstAirDate = input?.firstAirDate ?: "Unknown",
                    overview = input?.overview ?: "Unknown",
                    posterPath = input?.posterPath ?: "Unknown",
                    voteAverage = input?.voteAverage ?: 0.0,
                    name = input?.name ?: "Unknown",
                    tagLine = input?.tagLine ?: "Unknown",
                    episodeRunTime = input?.episodeRunTime ?: ArrayList(),
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
}