package id.madhanra.submission.favorite.di

import id.madhanra.submission.core.utils.Const
import id.madhanra.submission.favorite.movie.FavMovieViewModel
import id.madhanra.submission.favorite.tvShow.FavTvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val favoriteModule = module {
    scope(named(Const.VIEW_MODEL)) {
        viewModel { FavMovieViewModel(get()) }
        viewModel { FavTvShowViewModel(get()) }
    }
}