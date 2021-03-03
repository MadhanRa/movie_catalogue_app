package id.madhanra.submission.di

import id.madhanra.submission.core.domain.usecase.MoviesInteractor
import id.madhanra.submission.core.domain.usecase.MoviesUseCase
import id.madhanra.submission.core.domain.usecase.TvShowsInteractor
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase
import id.madhanra.submission.ui.detail.DetailViewModel
import id.madhanra.submission.ui.movie.MovieViewModel
import id.madhanra.submission.ui.tvShow.TvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MoviesUseCase> { MoviesInteractor(get()) }
    factory<TvShowsUseCase> { TvShowsInteractor(get()) }
}

val viewModelModule = module {
//    scope(named(Const.VIEW_MODEL)) {
//
//    }
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}