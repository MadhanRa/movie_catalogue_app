package id.madhanra.submission.core.di

import androidx.room.Room
import id.madhanra.submission.core.data.repository.MovieRepository
import id.madhanra.submission.core.data.repository.TvShowRepository
import id.madhanra.submission.core.data.source.local.LocalDataSource
import id.madhanra.submission.core.data.source.local.room.MovieCatalogueDatabase
import id.madhanra.submission.core.data.source.remote.retrofit.MovieApi
import id.madhanra.submission.core.data.source.remote.MovieRemoteDataSource
import id.madhanra.submission.core.data.source.remote.retrofit.TvShowApi
import id.madhanra.submission.core.data.source.remote.TvShowRemoteDataSource
import id.madhanra.submission.core.domain.repository.IMoviesRepository
import id.madhanra.submission.core.domain.repository.ITvShowsRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieCatalogueDatabase>().showDao() }
    single {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("movie_catalogue".toCharArray())
        val factory = SupportFactory(passPhrase)
        Room.databaseBuilder(
            androidContext(),
            MovieCatalogueDatabase::class.java,
            "MovieCatalogue.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MovieApi::class.java)
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(TvShowApi::class.java)
    }

}


val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { MovieRemoteDataSource(get()) }
    single { TvShowRemoteDataSource(get()) }
    single<IMoviesRepository> { MovieRepository(get(), get()) }
    single<ITvShowsRepository> { TvShowRepository(get(), get()) }
}