package id.madhanra.submission.core.di

import androidx.room.Room
import id.madhanra.submission.core.data.repository.MovieRepository
import id.madhanra.submission.core.data.repository.TvShowRepository
import id.madhanra.submission.core.data.source.local.MovieLocalDataSource
import id.madhanra.submission.core.data.source.local.TvShowLocalDataSource
import id.madhanra.submission.core.data.source.local.room.MovieCatalogueDatabase
import id.madhanra.submission.core.data.source.remote.MovieApi
import id.madhanra.submission.core.data.source.remote.MovieRemoteDataSource
import id.madhanra.submission.core.data.source.remote.TvShowApi
import id.madhanra.submission.core.data.source.remote.TvShowRemoteDataSource
import id.madhanra.submission.core.domain.repository.IMoviesRepository
import id.madhanra.submission.core.domain.repository.ITvShowsRepository
import io.reactivex.disposables.CompositeDisposable
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieCatalogueDatabase>().movieDao() }
    factory { get<MovieCatalogueDatabase>().tvShowDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieCatalogueDatabase::class.java,
            "MovieCatalogue"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

}

val networkModule = module {
    single {
        val hostname = "*.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MovieApi::class.java)
    }
    single {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        retrofit.create(TvShowApi::class.java)
    }

}


val repositoryModule = module {
    single { MovieLocalDataSource(get()) }
    single { TvShowLocalDataSource(get()) }
    single { MovieRemoteDataSource(get(), CompositeDisposable())}
    single { TvShowRemoteDataSource(get(), CompositeDisposable())}
    single<IMoviesRepository> { MovieRepository(get(), get()) }
    single<ITvShowsRepository> { TvShowRepository(get(), get())}
}