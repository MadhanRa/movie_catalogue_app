package id.madhanra.submission

import android.app.Application
import id.madhanra.submission.core.di.databaseModule
import id.madhanra.submission.core.di.networkModule
import id.madhanra.submission.core.di.repositoryModule
import id.madhanra.submission.di.useCaseModule
import id.madhanra.submission.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieCatalogueApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieCatalogueApplication)
            modules(
                listOf(
                    viewModelModule,
                    databaseModule,
                    useCaseModule,
                    repositoryModule,
                    networkModule
                )
            )
        }
    }
}