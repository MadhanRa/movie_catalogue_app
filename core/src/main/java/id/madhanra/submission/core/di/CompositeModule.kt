package id.madhanra.submission.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ApplicationComponent::class)
class CompositeModule {
    @Provides
    fun provideComposite(): CompositeDisposable {
        return CompositeDisposable()
    }
}