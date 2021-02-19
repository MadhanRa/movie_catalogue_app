package id.madhanra.submission.core.data.source.remote

import android.util.Log
import id.madhanra.submission.core.data.source.remote.response.DetailMovieResponse
import id.madhanra.submission.core.data.source.remote.response.MoviesItem
import id.madhanra.submission.core.utils.EspressoIdlingResource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
        private val apiService: MovieApi,
        private val compositeDisposable: CompositeDisposable
){
    fun getMovies(): Flowable<ApiResponse<List<MoviesItem>>>{
        EspressoIdlingResource.increment()
        val resultMovies = PublishSubject.create<ApiResponse<List<MoviesItem>>>()
        compositeDisposable.add(
                apiService.getPopularMovies(1)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .take(1)
                        .subscribe({ moviesResponse ->
                            resultMovies.onNext(if (moviesResponse.results.isNotEmpty()) ApiResponse.success(moviesResponse.results) else ApiResponse.empty("Nothing"))
                            if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                                EspressoIdlingResource.decrement()
                            }
                        }, { errorResponse ->
                            resultMovies.onNext(ApiResponse.error(errorResponse.message.toString()))
                            Log.e("GetMovies", errorResponse.toString())
                        })
        )
        return resultMovies.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailMovie(id: Int): Flowable<ApiResponse<DetailMovieResponse>>{
        EspressoIdlingResource.increment()
        val resultMovie = PublishSubject.create<ApiResponse<DetailMovieResponse>>()
        compositeDisposable.add(
                apiService.getMovieDetail(id)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .take(1)
                        .subscribe({ response ->
                            resultMovie.onNext(if (response.title.isNotEmpty()) ApiResponse.success(response) else ApiResponse.empty("Nothing"))
                            if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                                EspressoIdlingResource.decrement()
                            }
                        },{ errorResponse ->
                            resultMovie.onNext(ApiResponse.error(errorResponse.message.toString()))
                            Log.e("GetDetailMovie ", errorResponse.toString())
                        })
        )
        return resultMovie.toFlowable(BackpressureStrategy.BUFFER)
    }
}