package id.madhanra.submission.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.madhanra.submission.core.data.repository.MovieRepository
import id.madhanra.submission.core.data.repository.TvShowRepository
import id.madhanra.submission.core.domain.repository.IMoviesRepository
import id.madhanra.submission.core.domain.repository.ITvShowsRepository

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepository): IMoviesRepository

    @Binds
    abstract fun provideTvShowRepository(tvShowRepository: TvShowRepository): ITvShowsRepository
}