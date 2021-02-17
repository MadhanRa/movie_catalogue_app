package id.madhanra.submission.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(SingletonComponent::class)
class CompositeModule {
    @Provides
    fun provideComposite(): CompositeDisposable {
        return CompositeDisposable()
    }
}