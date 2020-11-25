package id.madhanra.submission.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {
    @Provides
    fun provideComposite(): CompositeDisposable {
        return CompositeDisposable()
    }
}