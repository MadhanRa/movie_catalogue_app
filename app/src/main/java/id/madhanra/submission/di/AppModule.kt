package id.madhanra.submission.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import id.madhanra.submission.core.domain.usecase.MoviesInteractor
import id.madhanra.submission.core.domain.usecase.MoviesUseCase
import id.madhanra.submission.core.domain.usecase.TvShowsInteractor
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideMovieUseCase(moviesInteractor: MoviesInteractor): MoviesUseCase

    @Binds
    abstract fun provideTvShowUseCase(tvShowsInteractor: TvShowsInteractor): TvShowsUseCase
}