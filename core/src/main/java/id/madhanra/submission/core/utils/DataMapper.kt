package id.madhanra.submission.core.utils

import id.madhanra.submission.core.data.source.local.entity.ShowEntity
import id.madhanra.submission.core.data.source.remote.response.MoviesItem
import id.madhanra.submission.core.data.source.remote.response.TvShowsItem
import id.madhanra.submission.core.domain.model.Show

object DataMapper {

    fun mapDomainToEntity(input: Show) = ShowEntity(
        id = input.id,
        title = input.title,
        releaseDate = input.releaseDate,
        overview = input.overview,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        showType = input.showType,
        isFavorite = input.isFavorite,
        isSearch = input.isSearch
    )


    fun mapEntityToDomain(input: ShowEntity?) = Show(
        id = input?.id ?: "Unknown",
        title = input?.title ?: "Unknown",
        releaseDate = input?.releaseDate ?: "Unknown",
        overview = input?.overview ?: "Unknown",
        posterPath = input?.posterPath ?: "Unknown",
        backdropPath = input?.backdropPath ?: "Unknown",
        showType = input?.showType ?: 0,
        isFavorite = input?.isFavorite ?: 0,
        isSearch = input?.isSearch ?: 0
    )

    fun mapMovieResponseToEntity(
        input: MoviesItem?,
        isFavorite: Int? = 0,
        isSearch: Int? = 0
    ) = ShowEntity(
        id = input?.id ?: "Unknown",
        title = input?.title ?: "Unknown",
        releaseDate = input?.releaseDate ?: "Unknown",
        overview = input?.overview ?: "Unknown",
        posterPath = input?.posterPath ?: "Unknown",
        backdropPath = input?.backdropPath ?: "Unknown",
        showType = Const.MOVIE_TYPE,
        isFavorite = isFavorite,
        isSearch = isSearch
    )

    fun mapTvShowResponseToEntity(
        input: TvShowsItem?,
        isFavorite: Int? = 0,
        isSearch: Int? = 0
    ) = ShowEntity(
        id = input?.id ?: "Unknown",
        title = input?.name ?: "Unknown",
        releaseDate = input?.firstAirDate ?: "Unknown",
        overview = input?.overview ?: "Unknown",
        posterPath = input?.posterPath ?: "Unknown",
        backdropPath = input?.backdropPath ?: "Unknown",
        showType = Const.TV_SHOW_TYPE,
        isFavorite = isFavorite,
        isSearch = isSearch
    )


    fun mapMovieResponseToDomain(
        input: MoviesItem?,
        isFavorite: Int? = 0,
        isSearch: Int? = 0
    ) = Show(
        id = input?.id ?: "Unknown",
        title = input?.title ?: "Unknown",
        releaseDate = input?.releaseDate ?: "Unknown",
        overview = input?.overview ?: "Unknown",
        posterPath = input?.posterPath ?: "Unknown",
        backdropPath = input?.backdropPath ?: "Unknown",
        showType = Const.MOVIE_TYPE,
        isFavorite = isFavorite ?: 0,
        isSearch = isSearch ?: 0
    )

    fun mapTvShowResponseToDomain(
        input: TvShowsItem?,
        isFavorite: Int? = 0,
        isSearch: Int? = 0
    ) = Show(
        id = input?.id ?: "Unknown",
        title = input?.name ?: "Unknown",
        releaseDate = input?.firstAirDate ?: "Unknown",
        overview = input?.overview ?: "Unknown",
        posterPath = input?.posterPath ?: "Unknown",
        backdropPath = input?.backdropPath ?: "Unknown",
        showType = Const.TV_SHOW_TYPE,
        isFavorite = isFavorite ?: 0,
        isSearch = isSearch ?: 0
    )

    fun mapListEntityToDomain(input: List<ShowEntity>): List<Show> =
        input.map {
            mapEntityToDomain(it)
        }
}