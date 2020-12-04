package id.madhanra.submission.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.madhanra.submission.data.source.local.room.MovieCatalogueDatabase
import id.madhanra.submission.data.source.local.room.MovieDao
import id.madhanra.submission.data.source.local.room.TvShowDao
import id.madhanra.submission.data.source.remote.MovieApi
import id.madhanra.submission.data.source.remote.TvShowApi
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {
    @Provides
    fun provideComposite(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowApi(retrofit: Retrofit): TvShowApi {
        return retrofit.create(TvShowApi::class.java)
    }

    @Provides
    fun provideMovieDao(movieCatalogueDatabase: MovieCatalogueDatabase): MovieDao {
        return movieCatalogueDatabase.movieDao()
    }

    @Provides
    fun provideTvShowDao(movieCatalogueDatabase: MovieCatalogueDatabase): TvShowDao {
        return movieCatalogueDatabase.tvShowDao()
    }

}