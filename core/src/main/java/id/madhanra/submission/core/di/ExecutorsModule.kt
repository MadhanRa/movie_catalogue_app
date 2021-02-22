package id.madhanra.submission.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.madhanra.submission.core.utils.AppExecutors
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ExecutorsModule {
    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }
}