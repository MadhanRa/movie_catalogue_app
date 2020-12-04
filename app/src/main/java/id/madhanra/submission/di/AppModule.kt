package id.madhanra.submission.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.madhanra.submission.data.source.local.room.MovieCatalogueDatabase
import id.madhanra.submission.utils.AppExecutors
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieCatalogueDatabase(application: Application): MovieCatalogueDatabase{
        return Room.databaseBuilder(
            application.applicationContext,
            MovieCatalogueDatabase::class.java,
            "MovieCatalogue.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }
}