package id.madhanra.submission.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.madhanra.submission.core.data.source.local.room.MovieCatalogueDatabase
import id.madhanra.submission.core.data.source.local.room.MovieDao
import id.madhanra.submission.core.data.source.local.room.TvShowDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieCatalogueDatabase(@ApplicationContext context: Context): MovieCatalogueDatabase{
        return Room.databaseBuilder(
            context,
            MovieCatalogueDatabase::class.java,
            "MovieCatalogue.db"
        ).fallbackToDestructiveMigration().build()
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